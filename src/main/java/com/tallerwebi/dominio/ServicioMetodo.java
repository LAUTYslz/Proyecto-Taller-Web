package com.tallerwebi.dominio;

public interface ServicioMetodo {
    Metodo buscarMetodoPorId(Long metodoId);

    Metodo buscarMetodoPorNombre(String nombreMetodo);
}
