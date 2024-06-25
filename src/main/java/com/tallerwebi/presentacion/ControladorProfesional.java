package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorProfesional {

    private final ServicioProfesional servicioContacto;
    private final ServicioLogin servicioLogin;

    @Autowired
    public ControladorProfesional(ServicioProfesional servicioContacto, ServicioLogin servicioLogin){
        this.servicioContacto = servicioContacto;
        this.servicioLogin = servicioLogin;
    }

    @GetMapping("/homeProfesional")
    public ModelAndView mostrarBienvenido(Model model, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        // Obtener el usuario actual
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        // Agregar el usuario
        //modelAndView.addObject("usuario", usuario);
        //List<Etapa> etapas = servicioAdmi.listaDeEtapas();
        //List<Juego> juegos = servicioAdmi.listasDeJuegos();

        // Agregar las listas al modelo para que estén disponibles en la vista
        //model.addAttribute("etapas", etapas);
        //model.addAttribute("juegos", juegos);
        model.addAttribute("usuario", usuario);
        // Establecer la vista
        modelAndView.setViewName("home_profesional");

        return modelAndView;
    }


    @PostMapping("/profesionales")
    public String agregarContacto(Profesional contacto) {
        servicioContacto.guardar(contacto);
        return "redirect:/profesionales";
    }


    public List<Profesional> obtenerProfesionalesPorMetodo(String nombreMetodo) {
        return servicioContacto.traerProfesionalesPorMetodo(nombreMetodo);
    }

    public List<Profesional> obtenerProfesionalesPorTipo(String nombreTipo) {
        return servicioContacto.traerProfesionalesPorTipo(nombreTipo);
    }

    public List<Profesional> obtenerProfesionales() {
        return servicioContacto.traerProfesionales();
    }

    public List<Profesional> obtenerProfesionalesPorMetodoYTipo(String nombreTipo, String nombreMetodo) {
        return servicioContacto.traerProfesionalesPorTipoYMetodo(nombreTipo, nombreMetodo);
    }

    @GetMapping("/profesional")
    public ModelAndView mostrarPaginaProfesionales(
            @RequestParam(value = "metodo", required = false) String nombreMetodo,
            @RequestParam(value = "tipo", required = false) String nombreTipo)
    {
        ModelAndView mav = new ModelAndView("profesional");

        try {
            List<Profesional> profesionales = obtenerProfesionalesPorMetodoYTipo(nombreTipo, nombreMetodo);

            mav.addObject("profesionales", profesionales);
            mav.addObject("metodos", servicioContacto.traerTodosLosMetodos());
            mav.addObject("tipos", servicioContacto.traerTodosLosTipos());
        } catch (Exception e) {
            mav.addObject("error", e.getMessage());

        }
        return mav;
    }


    @GetMapping("/filtrar")
    public ModelAndView filtrarContactosPorMetodoYTipo(String nombreMetodo, String nombreTipo) {
        ModelAndView mav = new ModelAndView("/profesional/filtrarPorMetodoYTipo");
        mav.addObject("ContactosFiltradosPorTipoYMetodo", servicioContacto.traerProfesionalesPorTipoYMetodo(nombreTipo, nombreMetodo));
        return mav;
    }
}
