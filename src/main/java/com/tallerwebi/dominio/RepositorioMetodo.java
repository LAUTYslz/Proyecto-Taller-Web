package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioMetodo {
    Metodo guardar(Metodo metodo);

    Metodo buscarPorNombre(String nombreMetodo);

    List<Metodo> buscarMetodos();
}
