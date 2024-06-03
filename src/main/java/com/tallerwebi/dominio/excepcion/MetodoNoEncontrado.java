package com.tallerwebi.dominio.excepcion;

public class MetodoNoEncontrado extends RuntimeException {
    public MetodoNoEncontrado(String nombreMetodo) {
        super("No se udo encontrar el método de especialización con el nombre: " + nombreMetodo);
    }
}
