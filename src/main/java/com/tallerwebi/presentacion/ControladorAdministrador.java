package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EtapaInexistente;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorAdministrador {

    private ServicioLogin servicioLogin;
    private ServicioAdmi servicioAdmi;

    @Autowired
    public ControladorAdministrador(ServicioLogin servicioLogin, ServicioAdmi servicioAdmi) {
        this.servicioLogin = servicioLogin;
        this.servicioAdmi = servicioAdmi;
    }


    @GetMapping("/administrador")
    public ModelAndView mostrarBienvenido(Model model, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        // Obtener el usuario actual
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        // Agregar el usuario
        modelAndView.addObject("usuario", usuario);
        List<Etapa> etapas = servicioAdmi.listaDeEtapas();
        List<Juego> juegos = servicioAdmi.listasDeJuegos();

        // Agregar las listas al modelo para que estén disponibles en la vista
        model.addAttribute("etapas", etapas);
        model.addAttribute("juegos", juegos);

        // Establecer la vista
        modelAndView.setViewName("administrador");

        return modelAndView;
    }

    @PostMapping("/administrador")
    public ModelAndView mostrarBienvenidoPost(Model model, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        // Obtener el usuario actual
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        // Agregar el usuario
        modelAndView.addObject("usuario", usuario);
        List<Etapa> etapas = servicioAdmi.listaDeEtapas();
        List<Juego> juegos = servicioAdmi.listasDeJuegos();

        // Agregar las listas al modelo para que estén disponibles en la vista
        model.addAttribute("etapas", etapas);
        model.addAttribute("juegos", juegos);

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

    @GetMapping("/crearEtapa")
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
    }


    @GetMapping("/crearJuego")
    public ModelAndView mostrarFormularioCrearJuego(Model model) {
        ModelAndView modelAndView = new ModelAndView("crearJuego");
        model.addAttribute("juego", new Juego());
        List<Etapa> etapasCreadas = servicioAdmi.listaDeEtapas();
        modelAndView.addObject("etapasCreadas", etapasCreadas);
        return modelAndView;
    }


    @PostMapping("/guardar-juego")
    public String crearJuego(Juego juego, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute(juego);
        modelAndView.addObject("juego", juego);
        servicioAdmi.guardarJuego(juego);
    return  "redirect:/administrador";

    }

    @PostMapping("/modificar-etapa/{id}")
    public String mostrarFormularioModificarEtapa(@PathVariable Long id, Model model) throws EtapaInexistente {

        Etapa etapaBuscada =servicioAdmi.buscarEtapa(id);
       model.addAttribute(etapaBuscada);
        return "guardar-etapa";
    }

    @PostMapping("/actualizar-etapa")
    public String modificarEtapa(@ModelAttribute Etapa etapa) throws EtapaInexistente {
   servicioAdmi.actualizarEtapa(etapa);

        return "redirect:/administrador"; // Redirigir a alguna página después de la modificación
    }


}








