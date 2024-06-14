package com.tallerwebi.dominio.excepcion;

public class NoPudoGuardarseElProfesional extends RuntimeException {
    public NoPudoGuardarseElProfesional() {
        super("No se puede guardar el profesional");
    }
}
