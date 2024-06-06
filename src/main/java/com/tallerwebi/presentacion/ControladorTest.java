package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ModeloTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/seleccionTests")
    public ModelAndView mostrarTests() {
        return new ModelAndView("seleccionTests");
    }

    @RequestMapping("/testAutismo")
    public ModelAndView mostrarFormularioAutismo() {
        ModelMap modelo = new ModelMap();
        modelo.put("modeloTest", new ModeloTest());
        return new ModelAndView("testAutismo", modelo);
    }

    @RequestMapping("/testAsperger")
    public ModelAndView mostrarFormularioAsperger() {
        ModelMap modelo = new ModelMap();
        modelo.put("modeloTest", new ModeloTest());
        return new ModelAndView("testAsperger", modelo);
    }

    @RequestMapping("/testAutismo/submit")
    public ModelAndView procesarTestAutismo(@ModelAttribute("modeloTest") ModeloTest modeloTest, ModelMap model) {
        String resultado = servicioTest.calcularResultadoAutismo(modeloTest);
        model.put("resultado", resultado);
        return new ModelAndView("resultadoTest", model);
    }



    @RequestMapping("/testAsperger/submit")
    public ModelAndView procesarTestAsperger(@ModelAttribute("modeloTest") ModeloTest modeloTest, ModelMap model) {
        String resultado = servicioTest.calcularResultadoAsperger(modeloTest);
        model.put("resultado", resultado);
        return new ModelAndView("resultadoTest", model);
    }
}
