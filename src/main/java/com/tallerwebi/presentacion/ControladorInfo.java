package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorInfo    {

    @RequestMapping(path="/info")
    public ModelAndView irAInfo(){
        ModelAndView modelo = new ModelAndView("info");
        return modelo;
    }

}
