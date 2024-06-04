package com.tallerwebi.dominio.excepcion;

public class NoSeEcncontraronContactosEnLaBusqueda extends RuntimeException {
    public NoSeEcncontraronContactosEnLaBusqueda() {
        super("No se encuentra contacto para la busqueda realizada");
    }
}
