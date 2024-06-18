package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioMetodo {
    Metodo guardar(Metodo metodo);

    Metodo buscarPorNombreDeMetodo(String nombreMetodo);

    List<Metodo> buscarMetodos();

    Metodo traerMetodoPorId(Long metodoId);

    Metodo buscarMetodoPorId(Long metodoId);
}
