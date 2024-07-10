package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioTipoProfesional {
    TipoProfesional guardar(TipoProfesional tipo);

    TipoProfesional buscarPorNombreDeTipo(String nombreTipo);

    List<TipoProfesional> buscarTipos();

    TipoProfesional traerTipoPorId(Long tipoId);

    List<TipoProfesional> buscarTiposSinTienda();
}
