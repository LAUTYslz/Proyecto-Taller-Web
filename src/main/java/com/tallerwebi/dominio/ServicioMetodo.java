package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioMetodo {
    Metodo buscarMetodoPorId(Long metodoId);

    Metodo buscarMetodoPorNombre(String nombreMetodo);

    List<Metodo> buscarMetodos();

    void asociarHijo(Long hijoId, Long metodoId);
}
