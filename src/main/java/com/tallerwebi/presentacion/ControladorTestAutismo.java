package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioTestAutismo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorTestAutismo {

    private ServicioTestAutismo servicioTestAutismo;

    // se le inyecta en el constructor el servicio
public ControladorTestAutismo(ServicioTestAutismo servicioTestAutismo) {this.servicioTestAutismo = servicioTestAutismo;}

    @RequestMapping(path ="test")
    public ModelAndView irATest(){
        return new ModelAndView("test");
    }

    @RequestMapping(path = "/test/submit", method = RequestMethod.POST)
    public ModelAndView procesarFormulario(@RequestParam("pregunta1") String pregunta1,
                                           @RequestParam("pregunta2") String pregunta2,
                                           @RequestParam("pregunta3") String pregunta3,
                                           @RequestParam("pregunta4") String pregunta4,
                                           @RequestParam("pregunta5") String pregunta5,
                                           @RequestParam("pregunta6") String pregunta6,
                                           @RequestParam("pregunta7") String pregunta7,
                                           @RequestParam("pregunta8") String pregunta8,
                                           @RequestParam("pregunta9") String pregunta9,
                                           @RequestParam("pregunta10") String pregunta10) {
        String[] respuestas = {pregunta1, pregunta2, pregunta3, pregunta4, pregunta5,
                pregunta6, pregunta7, pregunta8, pregunta9, pregunta10};
        String resultado = servicioTestAutismo.calcularResultado(respuestas);
        ModelMap modelo = new ModelMap();

        modelo.put("resultado", resultado);
        return new ModelAndView("resultado", modelo);
    }
}



