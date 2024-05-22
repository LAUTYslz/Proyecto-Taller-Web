package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Hijo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorBienvenido {
    @RequestMapping(path = "/bienvenido")
    public ModelAndView irABienvenido() {
        return new ModelAndView("bienvenido");
    }


    //creo un hijo
    @RequestMapping(path = "/nuevos")
    public ModelAndView irAaLTA() {
        ModelMap modelo = new ModelMap();
        modelo.put("hijo", new Hijo());
        return new ModelAndView("nuevos", modelo);

    }

    @RequestMapping(path = "/guardar-hijo", method = RequestMethod.POST)
    public ModelAndView guardarHijo(@ModelAttribute("hijo") Hijo hijo ,HttpServletRequest request) {
        ModelMap modelo = new ModelMap();

        //aca tiene que ir la logica de buscar si hijo existe en la registro-
        modelo.put("hijo", new Hijo());
        return new ModelAndView("nuevos", modelo);

    }


}

