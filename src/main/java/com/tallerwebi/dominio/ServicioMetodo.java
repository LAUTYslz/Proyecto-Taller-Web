package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioMetodo {

    List<Metodo> buscarMetodos();

    Hijo asociarHijo(Long hijoId, Long metodoId);
}
