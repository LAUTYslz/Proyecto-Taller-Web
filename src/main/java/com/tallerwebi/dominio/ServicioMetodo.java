package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioMetodo {

    List<Metodo> buscarMetodos();

    void asociarHijo(Long hijoId, Long metodoId);
}
