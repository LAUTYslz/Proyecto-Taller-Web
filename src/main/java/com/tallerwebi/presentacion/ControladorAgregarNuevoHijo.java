package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorAgregarNuevoHijo
{
    @RequestMapping(path="/agregar-nuevo-hijo")
    public ModelAndView irAInfo(){
        return new ModelAndView("agregar-nuevo-hijo");
    }
}
