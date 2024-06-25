package com.tallerwebi.dominio.excepcion;

public class ElUsuarioYatieneTurnoConElProfesional extends RuntimeException {
    public ElUsuarioYatieneTurnoConElProfesional() {
        super("Ya tienes un turno pendiente o confirmado con este profesional.");
    }
}
