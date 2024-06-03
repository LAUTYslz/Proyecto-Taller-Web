package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioTipoContacto {
    TipoContacto guardar(TipoContacto tipo);

    TipoContacto buscarPorNombre(String nombreTipo);

    List<TipoContacto> buscarTipos();
}
