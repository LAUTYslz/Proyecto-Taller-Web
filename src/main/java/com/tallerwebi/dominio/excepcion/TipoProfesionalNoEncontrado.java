package com.tallerwebi.dominio.excepcion;

public class TipoProfesionalNoEncontrado extends RuntimeException {
    public TipoProfesionalNoEncontrado(String nombreTipoContacto) {
        super("No se pudo encontrar el tipo de contacto con el nombre: " + nombreTipoContacto);
    }
}
