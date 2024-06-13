package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EtapaInexistente;
import com.tallerwebi.dominio.excepcion.juegoInexistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorAdministrador {

    private final HttpServletRequest httpServletRequest;
    private ServicioLogin servicioLogin;
    private ServicioAdmi servicioAdmi;

    @Autowired
    public ControladorAdministrador(ServicioLogin servicioLogin, ServicioAdmi servicioAdmi, HttpServletRequest httpServletRequest) {
        this.servicioLogin = servicioLogin;
        this.servicioAdmi = servicioAdmi;
        this.httpServletRequest = httpServletRequest;
    }

    @GetMapping("/administrador")
    public ModelAndView mostrarBienvenido(Model model, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        // Obtener el usuario actual
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        // Agregar el usuario
        modelAndView.addObject("usuario", usuario);

        // Verificar si ya se han cargado las etapas y los juegos en el modelo
        if (!model.containsAttribute("etapas")) {
            List<Etapa> etapas = servicioAdmi.listaDeEtapas();
            model.addAttribute("etapas", etapas);
        }

        if (!model.containsAttribute("juegos")) {
            List<Juego> juegos = servicioAdmi.listasDeJuegos();
            model.addAttribute("juegos", juegos);
        }

        // Establecer la vista
        modelAndView.setViewName("administrador");

        return modelAndView;
    }


    @GetMapping("/cerrar-sesion")
    public String cerrarSesion(HttpServletRequest request, HttpSession session) {
        // Invalidar la sesión y redirigir al inicio
        session.invalidate();
        return "redirect:/";
    }

   /* @GetMapping("/crearEtapa")
    public String mostrarFormularioCreacionEtapa(Model model) {
        model.addAttribute("etapa", new Etapa());
        return "crearEtapa"; // Asegúrate de que este sea el nombre correcto de tu vista
    }

    @PostMapping("/guardar-etapa")
    public String crearEtapa(Etapa etapa) {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("etapa", etapa);
        servicioAdmi.guardarEtapa(etapa);
        return "redirect:/crearJuego";
    }*/


   /* @GetMapping("/crearJuego")
    public ModelAndView mostrarFormularioCrearJuego(Model model) {
        ModelAndView modelAndView = new ModelAndView("crearJuego");
        model.addAttribute("juego", new Juego());
        List<Etapa> etapasCreadas = servicioAdmi.listaDeEtapas();
        modelAndView.addObject("etapasCreadas", etapasCreadas);
        return modelAndView;
    }*/


   /* @PostMapping("/guardar-juego")
    public String crearJuego(Juego juego, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute(juego);
        modelAndView.addObject("juego", juego);
        servicioAdmi.guardarJuego(juego);
    return  "redirect:/administrador";

    }*/

    @PostMapping("/modificar-etapa/{id}")
    public String mostrarFormularioModificarEtapa(@PathVariable Long id, Model model) throws EtapaInexistente {

        Etapa etapaBuscada = servicioAdmi.buscarEtapa(id);
        model.addAttribute(etapaBuscada);
        return "guardar-etapa";
    }


    @PostMapping("/actualizar-etapa")
    public String modificarEtapa(@ModelAttribute Etapa etapa) throws EtapaInexistente {
        servicioAdmi.actualizarEtapa(etapa);

        return "redirect:/administrador"; // Redirigir a alguna página después de la modificación
    }

    @PostMapping("/eliminar-etapa/{id}")
    public String eliminarEtapa(@ModelAttribute Etapa etapa) {
        servicioAdmi.eliminarEtapa(etapa);

        return "redirect:/administrador"; // Redirigir a alguna página después de la modificación
    }

    @PostMapping("/verjuego-etapa/{id}")
    public String verJuegosPorEtapa(@PathVariable Long id, Model model) throws EtapaInexistente {
        Usuario recupero = servicioLogin.obtenerUsuarioActual(httpServletRequest);
        List<Juego> juegos = servicioAdmi.listasDeJuegosPorEtapa(id);
        model.addAttribute("juegos", juegos);
        Etapa etapaBuscada = servicioAdmi.buscarEtapa(id);
        model.addAttribute(etapaBuscada);
        if (recupero.getRol().equalsIgnoreCase("ADMIN")) {
            return "verJuegoPorEtapaAdmi";
        } else {
            return "verJuegoPorEtapaUsuario";
        }
    }

    @GetMapping("/verjuego")
    public String verJuegoPorEtapa(HttpServletRequest request) {
        Usuario recupero = servicioLogin.obtenerUsuarioActual(request);
        if (recupero != null) {

            request.getSession().setAttribute("ROL", recupero.getRol());
            if (recupero.getRol().equals("ADMIN")) {
                return "verJuegoPorEtapaAdmin";
            }


        }
        return "verJuegoPorEtapaUsuario";
    }


    @GetMapping("/modificar-juego/{id}")
    public String mostrarFormularioDeModificarJuego(@PathVariable Long id, Model model) throws juegoInexistente {
     Juego buscado=   servicioAdmi.buscarJuegoPorId(id);
    model.addAttribute(buscado);
      return "modificarJuego";
    }
    @GetMapping("/actualizar-juego")
    public String modificarJuego(@ModelAttribute Juego juego) throws juegoInexistente {
        servicioAdmi.actualizarJuego(juego);

        return "redirect:/administrador";
    }
    @GetMapping("/verModificacionjuego")
    public String verModificacionJuegoPorEtapa() {

        return "modificarJuego";
    }
    @GetMapping("/eliminar-juego/{id}")
    public String eliminarJuego(@PathVariable Long id, Model model) throws juegoInexistente {
        Juego buscado=   servicioAdmi.buscarJuegoPorId(id);
        servicioAdmi.eliminarJuego(buscado);
        return "redirect:/administrador";
    }


}








