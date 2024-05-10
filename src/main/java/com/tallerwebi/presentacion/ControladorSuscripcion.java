package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorSuscripcion {

    @RequestMapping("/suscripcion")
    public ModelAndView irASuscripcion() {
        return new ModelAndView("suscripcion");
    }

    @RequestMapping("/membresiaPaga")
    public ModelAndView irAMembresiaPaga() {
        return new ModelAndView("membresiaPaga");
    }

    @RequestMapping(path = "/membresiaPaga")
    public ModelAndView nuevaMembresia() {
        ModelMap model = new ModelMap();
        model.put("nuevaMembresiaPaga", new DatosMembresia());
        return new ModelAndView("nuevo-usuario", model);
    }

    @GetMapping("/membresiaPaga")
    public ModelAndView procesarDatosDeMembresiaPaga(@ModelAttribute("datosMembresia") DatosMembresia datosMembresia){
        ModelMap model = new ModelMap();
        model.put("datosMembresia", datosMembresia);
        return new ModelAndView("confirmacionMembresia", model);
    }


}
