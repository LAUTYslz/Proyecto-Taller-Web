package com.tallerwebi.dominio;

public interface ServicioTipoProfesional {
    TipoProfesional buscarTipoPorId(Long tipoId);

    TipoProfesional buscarTipoPorNombre(String nombreMetodo);
}
