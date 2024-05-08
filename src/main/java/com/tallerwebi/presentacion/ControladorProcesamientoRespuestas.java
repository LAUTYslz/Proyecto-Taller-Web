package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioProcesamientoRespuestas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorProcesamientoRespuestas {

    private final ServicioProcesamientoRespuestas servicioProcesamientoRespuestas;

    @Autowired
    public ControladorProcesamientoRespuestas(ServicioProcesamientoRespuestas servicioProcesamientoRespuestas) {
        this.servicioProcesamientoRespuestas = servicioProcesamientoRespuestas;
    }

    @PostMapping("/resultadoTest")
    public ModelAndView procesarRespuestas(String pregunta1, String pregunta2, String pregunta3, String pregunta4, String pregunta5, String pregunta6, String pregunta7, String pregunta8, String pregunta9, String pregunta10, Model model) {
        ResultadoProcesamiento resultado = servicioProcesamientoRespuestas.procesarRespuestas(pregunta1, pregunta2, pregunta3, pregunta4, pregunta5, pregunta6, pregunta7, pregunta8, pregunta9, pregunta10);
        model.addAttribute("resultado", resultado);
        return new ModelAndView("resultadoTest"); // Esta es la vista que mostrará los resultados
    }
}