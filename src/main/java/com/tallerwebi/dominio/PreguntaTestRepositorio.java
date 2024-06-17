package com.tallerwebi.dominio;

import java.util.List;
import java.util.Optional;

public interface PreguntaTestRepositorio {
    void guardar(PreguntaTest preguntaTest);
    List<PreguntaTest> obtenerTodos();
    void editarPregunta(PreguntaTest preguntaTest);
    void eliminarPorId(Long id);

    Optional<PreguntaTest> findById(Long id);

    List<PreguntaTest> obtenerPreguntasPorModeloTestId(Long modeloTestId);
}
