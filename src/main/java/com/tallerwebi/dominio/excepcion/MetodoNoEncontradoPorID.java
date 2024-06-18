package com.tallerwebi.dominio.excepcion;

public class MetodoNoEncontradoPorID extends RuntimeException {
    public MetodoNoEncontradoPorID(Long idMetodo) {
        super("No se udo encontrar el método de especialización con el id: " + idMetodo);
    }
}
