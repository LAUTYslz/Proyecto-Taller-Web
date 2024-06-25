package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EtapaInexistente;
import com.tallerwebi.dominio.excepcion.juegoInexistente;
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
        List<Juego> lista= servicioAdmi.listasDeJuegosPorEtapa(etapaBuscada.getId());
        model.addAttribute("juegos", lista);
        return "verJuegoPorEtapaAdmi";
    }
    @GetMapping("/modificar-juego/{id}")
    public String mostarformJuego(@PathVariable Long id, Model model) throws EtapaInexistente, juegoInexistente {

        Juego juegoBuscado =servicioAdmi.buscarJuegoPorId(id);
        model.addAttribute(juegoBuscado);

        return "modificarJuego";
    }
    @PostMapping("/actualizar-juego/{id}")
    public String modificarJuego(@PathVariable Long id, Model model) throws EtapaInexistente, juegoInexistente {

        Juego juegoBuscado =servicioAdmi.buscarJuegoPorId(id);
        model.addAttribute(juegoBuscado);
        servicioAdmi.actualizarJuego(juegoBuscado);
        return "verJuegoPorEtapaAdmi";
    }


    @PostMapping("/eliminar-juego/{id}")
    public String eliminarJuego(@PathVariable Long id, Model model) throws EtapaInexistente, juegoInexistente {

        Juego juegoBuscado =servicioAdmi.buscarJuegoPorId(id);
        servicioAdmi.eliminarJuego(juegoBuscado);
        return "verJuegoPorEtapaAdmi";
    }

    @GetMapping("/verjuego")
    public String verJuegoPorEtapa() {
        // Aquí podrías agregar lógica para obtener los datos del juego relacionados con la etapa
        // y pasarlos a la vista, pero por ahora, simplemente devolveremos la vista sin datos
        return "verJuegoPorEtapaAdmi";
    }
    @GetMapping("/verjuegoModificado")
    public String verJuegoMoa() {
        // Aquí podrías agregar lógica para obtener los datos del juego relacionados con la etapa
        // y pasarlos a la vista, pero por ahora, simplemente devolveremos la vista sin datos
        return "modificarJuego";
    }


    //----------------------PROFESIONALES---------------------------------------
    @GetMapping("/admin/gestionarProfesionales")
    public ModelAndView verGestionarProfesionales(
            @RequestParam(value = "metodo", required = false) String nombreMetodo,
            @RequestParam(value = "tipo", required = false) String nombreTipo)
    {
        ModelAndView mav = new ModelAndView("gestionarProfesionales");
        try {
            List<Profesional> profesionales = obtenerProfesionalesPorMetodoYTipo(nombreTipo, nombreMetodo);

            mav.addObject("profesionales", profesionales);
            mav.addObject("metodos", servicioProfesional.traerTodosLosMetodos());
            mav.addObject("tipos", servicioProfesional.traerTodosLosTipos());
        } catch (Exception e) {
            mav.addObject("error", e.getMessage());

        }
        //List<Profesional> profesionales = servicioProfesional.traerProfesionales();
        //mav.addObject("profesionales", profesionales);
        return mav;
    }

    public List<Profesional> obtenerProfesionalesPorMetodoYTipo(String nombreTipo, String nombreMetodo) {
        return servicioProfesional.traerProfesionalesPorTipoYMetodo(nombreTipo, nombreMetodo);
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

            Usuario usuarioProf = new Usuario();
            usuarioProf.setNombre(nombre);
            usuarioProf.setEmail(email);

            servicioProfesional.guardarProfesional(profesional,nombreMetodo,nombreTipo);
            servicioLogin.registrarUsuarioProfesional(usuarioProf);
            mav.setViewName ("redirect:/admin/gestionarProfesionales");
        } catch (RuntimeException e) {
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
    public ModelAndView actualizarProfesional(@RequestParam("id") Long id,
                                              @RequestParam("nombre") String nombre,
                                              @RequestParam("telefono") String telefono,
                                              @RequestParam("email") String email,
                                              @RequestParam("direccion") String direccion,
                                              @RequestParam("institucion") String institucion,
                                              @RequestParam("tipo") String nombreTipo,
                                              @RequestParam("metodo") String nombreMetodo) {
        ModelAndView mav = new ModelAndView();
        try{
            Profesional profesional = servicioProfesional.obtenerPorId(id);
            if (profesional == null) {
                throw new Exception("No se encontró el profesional con el ID proporcionado.");
            }
            //Metodo metodo = servicioMetodo.buscarMetodoPorNombre(nombreMetodo);
            //TipoProfesional tipoProfesional = servicioTipoProfesional.buscarTipoPorNombre(nombreTipo);

            profesional.setNombre(nombre);
            profesional.setTelefono(telefono);
            profesional.setEmail(email);
            profesional.setDireccion(direccion);
            profesional.setInstitucion(institucion);


            servicioProfesional.actualizarProfesional(profesional, nombreMetodo, nombreTipo);
            mav.setViewName ("redirect:/admin/gestionarProfesionales");
        } catch (Exception e) {
            mav.setViewName ("formulario_editar_profesional");
            Profesional profesional = servicioProfesional.obtenerPorId(id);
            mav.addObject("error", e.getMessage());
            mav.addObject("profesional", profesional);
            List<Metodo> metodos = servicioProfesional.traerTodosLosMetodos();
            List<TipoProfesional> tipos = servicioProfesional.traerTodosLosTipos();
            mav.addObject("metodos", metodos);
            mav.addObject("tipos", tipos);

            System.err.println("Error al actualizar profesional: " + e.getMessage());
        }
        return mav;
    }

    @GetMapping("/admin/gestionarProfesionales/eliminar/{id}")
    public String eliminarProfesional(@PathVariable Long id) {
       Profesional profesional = servicioProfesional.obtenerPorId(id);
        servicioProfesional.eliminarProfesional(profesional);
        return "redirect:/admin/gestionarProfesionales";
    }
}








