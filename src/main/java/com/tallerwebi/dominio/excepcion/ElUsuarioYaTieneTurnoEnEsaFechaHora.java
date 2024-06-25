package com.tallerwebi.dominio.excepcion;

public class ElUsuarioYaTieneTurnoEnEsaFechaHora extends RuntimeException {
    public ElUsuarioYaTieneTurnoEnEsaFechaHora() {
        super("Ya tienes un turno en esa fecha y hora");
    }
}
