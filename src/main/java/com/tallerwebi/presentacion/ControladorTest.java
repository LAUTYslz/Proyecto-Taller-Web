package com.tallerwebi.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.tallerwebi.dominio.ServicioTest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;


@Controller
public class ControladorTest {

    private ServicioTest servicioTest;

    @Autowired
    public ControladorTest(ServicioTest servicioTest){
        this.servicioTest = servicioTest;
    }

    @RequestMapping("/test")
    public ModelAndView mostrarFormulario() {
        ModelMap modelo = new ModelMap();
        modelo.put("respuestasTest", new RespuestasTest());
        return new ModelAndView("test", modelo);
    }

    @RequestMapping(path = "/test/submit", method = RequestMethod.POST)
    public ModelAndView procesarTest(@ModelAttribute("respuestasTest") RespuestasTest respuestasTest, ModelMap model) {
        String resultado = servicioTest.calcularResultado(respuestasTest);
        model.put("resultado", resultado);
        return new ModelAndView("resultado", model);
    }

}
