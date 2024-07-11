package com.tallerwebi.dominio.excepcion;

public class ElProfesionalNoTieneTurnosRealizados extends RuntimeException {
    public ElProfesionalNoTieneTurnosRealizados() {
        super("El profesional no tiene turnos realizados");
    }
}
