package com.tallerwebi.dominio.excepcion;

public class TipoContactoNoEncontrado extends RuntimeException {
    public TipoContactoNoEncontrado(String nombreTipoContacto) {
        super("No se pudo encontrar el tipo de contacto con el nombre: " + nombreTipoContacto);
    }
}
