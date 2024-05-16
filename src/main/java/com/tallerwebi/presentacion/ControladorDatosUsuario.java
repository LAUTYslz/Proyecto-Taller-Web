package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorDatosUsuario {

    @RequestMapping(path="/datos-usuario")
    public ModelAndView irAInfo(){
        return new ModelAndView("datos-usuario");
    }
}
