package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorDatosUsuario {

    @RequestMapping(path="/datos-usuario")
    public ModelAndView IraDatosUsuario(){
        return new ModelAndView("datos-usuario");
    }

    @RequestMapping(path="/datos-usuario/modificar-datos-usuario")
    public ModelAndView IraModificarDatosUsuario(){
        return new ModelAndView("modificar-datos-usuario");
    }

    @RequestMapping(path="/datos-usuario/datos-hijo")
    public ModelAndView IraDatosHijo(){
        return new ModelAndView("datos-hijo");
    }

    @RequestMapping(path="/datos-usuario/datos-hijo/modificar-datos-hijo")
    public ModelAndView IraModificarDatosHijo(){
        return new ModelAndView("modificar-datos-hijo");
    }

    @RequestMapping(path="/datos-usuario/agregar-nuevo-hijo")
    public ModelAndView IraAgregarNuevoHijo(){
        return new ModelAndView("agregar-nuevo-hijo");
    }
}
