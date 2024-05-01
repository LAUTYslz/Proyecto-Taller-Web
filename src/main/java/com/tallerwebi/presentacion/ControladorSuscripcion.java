package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorSuscripcion {

    @RequestMapping("/suscripcion")
    public ModelAndView irASuscripcion() {
        ModelAndView modelo = new ModelAndView("suscripcion");
        return modelo;
    }
}
