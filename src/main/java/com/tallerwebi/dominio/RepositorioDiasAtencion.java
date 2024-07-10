package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioDiasAtencion {
    List<DiasAtencion> buscarTodos();

    void guardar(DiasAtencion diasAtencion);

    void eliminarPorProfesionalId(Long idProfesional);
}
