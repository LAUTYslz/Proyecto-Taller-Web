package com.tallerwebi.dominio.excepcion;

public class NoSeEncontraronProfesionalesEnLaBusqueda extends RuntimeException {
    public NoSeEncontraronProfesionalesEnLaBusqueda() {
        super("No se encuentra contacto para la busqueda realizada");
    }
}
