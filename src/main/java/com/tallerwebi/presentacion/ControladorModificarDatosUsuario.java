package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorModificarDatosUsuario {
    @RequestMapping(path="/modificar-datos-usuario")
    public ModelAndView irAInfo(){
        return new ModelAndView("modificar-datos-usuario");
    }
}
