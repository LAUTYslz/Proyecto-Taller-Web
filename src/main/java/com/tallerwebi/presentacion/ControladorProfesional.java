package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Profesional;
import com.tallerwebi.dominio.ServicioProfesional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorProfesional {

    private final ServicioProfesional servicioContacto;

    @Autowired
    public ControladorProfesional(ServicioProfesional servicioContacto){
        this.servicioContacto = servicioContacto;
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
            List<Profesional> profesionales = obtenerProfesionalesPorMetodoYTipo(nombreTipo, nombreMetodo);;

            mav.addObject("profesionales", profesionales);
            mav.addObject("metodos", servicioContacto.traerTodosLosMetodos());
            mav.addObject("tipos", servicioContacto.traerTodosLosTipos());
        } catch (Exception e) {
            mav.addObject("error", e.getMessage());

        }
        return mav;
    }


/*
    @GetMapping("/filtrar")
    public ModelAndView filtrarContactosPorMetodo(String nombreMetodo) {
        ModelAndView mav = new ModelAndView("contacto/filtrarPorMetodo");
        mav.addObject("ContactosFiltradosPorMetodo", servicioContacto.traerContactosPorMetodo(nombreMetodo));
        return mav;
    }

    @GetMapping("/filtrar")
    public ModelAndView filtrarContactosPorTipo(String nombreTipo) {
        ModelAndView mav = new ModelAndView("contacto/filtrarPorTipo");
        mav.addObject("ContactosFiltradosPorTipo", servicioContacto.traerContactosPorTipo(nombreTipo));
        return mav;
    }*/

    @GetMapping("/filtrar")
    public ModelAndView filtrarContactosPorMetodoYTipo(String nombreMetodo, String nombreTipo) {
        ModelAndView mav = new ModelAndView("/profesional/filtrarPorMetodoYTipo");
        mav.addObject("ContactosFiltradosPorTipoYMetodo", servicioContacto.traerProfesionalesPorTipoYMetodo(nombreTipo, nombreMetodo));
        return mav;
    }
}
