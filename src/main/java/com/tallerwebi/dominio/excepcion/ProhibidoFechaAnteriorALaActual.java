package com.tallerwebi.dominio.excepcion;

public class ProhibidoFechaAnteriorALaActual extends RuntimeException{
    public ProhibidoFechaAnteriorALaActual() {
        super("No puedes seleccionar una fecha anterior a la actual.");
    }
}
