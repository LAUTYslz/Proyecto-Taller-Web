package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioTipoContacto {
    TipoContacto guardar(TipoContacto tipo);

    TipoContacto buscarPorNombreDeTipo(String nombreTipo);

    List<TipoContacto> buscarTipos();
}
