package com.tallerwebi.dominio;

import java.util.List;
import java.util.Optional;

public interface ModeloTestRepositorio {
    void guardar(ModeloTest modeloTest);

    List<ModeloTest> obtenerTodos();

    Optional<ModeloTest> findById(Long id);

    void editarTest(ModeloTest modeloTest);
    void eliminarPorId(Long id);

    void save(ModeloTest modeloTest);
}
