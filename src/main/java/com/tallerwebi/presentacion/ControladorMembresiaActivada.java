package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorMembresiaActivada {
    private ServicioMembresiaActivada servicioMembresiaActivada;
    private ServicioLogin servicioLogin;
    private ServicioAdmi servicioAdmi;
    private ServicioProfesional servicioProfesional;
    private ServicioMetodo servicioMetodo;
    private ServicioTipoProfesional servicioTipoProfesional;

    public ControladorMembresiaActivada(ServicioMembresiaActivada servicioMembresiaActivada, ServicioLogin servicioLogin, ServicioAdmi servicioAdmi, ServicioProfesional servicioProfesional, ServicioMetodo servicioMetodo, ServicioTipoProfesional servicioTipoProfesional) {
        this.servicioMembresiaActivada = servicioMembresiaActivada;
        this.servicioLogin = servicioLogin;
        this.servicioAdmi = servicioAdmi;
        this.servicioProfesional = servicioProfesional;
        this.servicioMetodo = servicioMetodo;
        this.servicioTipoProfesional = servicioTipoProfesional;
    }
}
