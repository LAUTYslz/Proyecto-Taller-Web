package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EtapaInexistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorAdministrador {

    private ServicioLogin servicioLogin;
    private ServicioAdmi servicioAdmi;
    private ServicioProfesional servicioProfesional;
    private ServicioMetodo servicioMetodo;
    private ServicioTipoProfesional servicioTipoProfesional;

    @Autowired
    public ControladorAdministrador(ServicioLogin servicioLogin, ServicioAdmi servicioAdmi, ServicioProfesional servicioProfesional) {
        this.servicioLogin = servicioLogin;
        this.servicioAdmi = servicioAdmi;
        this.servicioProfesional = servicioProfesional;
        this.servicioMetodo = servicioMetodo;
        this.servicioTipoProfesional = servicioTipoProfesional;
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
    @PostMapping("/verjuego-etapa/{id}")
    public String verJuegosPorEtapa(@PathVariable Long id, Model model) throws EtapaInexistente {

        Etapa etapaBuscada =servicioAdmi.buscarEtapa(id);
        model.addAttribute(etapaBuscada);
        return "verJuegoPorEtapa";
    }

    @GetMapping("/verjuego")
    public String verJuegoPorEtapa() {
        // Aquí podrías agregar lógica para obtener los datos del juego relacionados con la etapa
        // y pasarlos a la vista, pero por ahora, simplemente devolveremos la vista sin datos
        return "verJuegoPorEtapa";
    }


    //----------------------PROFESIONALES---------------------------------------
    @GetMapping("/admin/gestionarProfesionales")
    public ModelAndView verGestionarProfesionales() {
        ModelAndView mav = new ModelAndView("gestionarProfesionales");
        List<Profesional> profesionales = servicioProfesional.traerProfesionales();
        mav.addObject("profesionales", profesionales);
        return mav;
    }

     @GetMapping("/admin/gestionarProfesionales/crear")
     public ModelAndView  mostrarFormularioNuevo() {
        ModelAndView mav = new ModelAndView("formulario_crear_profesional");
         mav.addObject("profesional", new Profesional());
         List<Metodo> metodos = servicioProfesional.traerTodosLosMetodos();
         List<TipoProfesional> tipos = servicioProfesional.traerTodosLosTipos();
         mav.addObject("metodos", metodos);
         mav.addObject("tipos", tipos);
         return mav;
     }

    @PostMapping("/admin/gestionarProfesionales/guardar")
    public ModelAndView agregarProfesional(
            @RequestParam("nombre") String nombre,
            @RequestParam("telefono") String telefono,
            @RequestParam("email") String email,
            @RequestParam("direccion") String direccion,
            @RequestParam("institucion") String institucion,
            @RequestParam("tipo") String nombreTipo,
            @RequestParam("metodo") String nombreMetodo)
    {
        ModelAndView mav = new ModelAndView();
        try{
            //TipoProfesional tipo = servicioTipoProfesional.buscarTipoPorId(tipoId);
            //Metodo metodo = servicioMetodo.buscarMetodoPorId(metodoId);


            Profesional profesional = new Profesional();
            profesional.setNombre(nombre);
            profesional.setTelefono(telefono);
            profesional.setEmail(email);
            profesional.setDireccion(direccion);
            profesional.setInstitucion(institucion);
            //profesional.setTipo(tipo);
            //profesional.setMetodo(metodo);


            servicioProfesional.guardarProfesional(profesional,nombreMetodo,nombreTipo);
            mav.setViewName ("redirect:/admin/gestionarProfesionales");
        } catch (Exception e) {
            mav.setViewName ("formulario_crear_profesional");
            mav.addObject("error", e.getMessage());
            mav.addObject("profesional", new Profesional());
            List<Metodo> metodos = servicioProfesional.traerTodosLosMetodos();
            List<TipoProfesional> tipos = servicioProfesional.traerTodosLosTipos();
            mav.addObject("metodos", metodos);
            mav.addObject("tipos", tipos);
        }
        return mav;
    }

    @GetMapping("/admin/gestionarProfesionales/editar/{id}")
    public ModelAndView editarProfesional(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("formulario_editar_profesional");
        Profesional profesional = servicioProfesional.obtenerPorId(id);
        mav.addObject("profesional", profesional);
        List<Metodo> metodos = servicioProfesional.traerTodosLosMetodos();
        List<TipoProfesional> tipos = servicioProfesional.traerTodosLosTipos();
        mav.addObject("metodos", metodos);
        mav.addObject("tipos", tipos);
        return mav;
    }

    @PostMapping("/admin/gestionarProfesionales/actualizar")
    public String actualizarProfesional(@ModelAttribute Profesional profesional) {
        servicioProfesional.actualizarProfesional(profesional);
        return "redirect:/admin/gestionarProfesionales";
    }

    @GetMapping("/admin/gestionarProfesionales/eliminar/{id}")
    public String eliminarProfesional(@PathVariable Long id) {
       Profesional profesional = servicioProfesional.obtenerPorId(id);
        servicioProfesional.eliminarProfesional(profesional);
        return "redirect:/admin/gestionarProfesionales";
    }
}








