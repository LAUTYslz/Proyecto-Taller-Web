package com.tallerwebi.dominio;
import java.util.List;
import java.util.Optional;

public interface RespuestaTestRepositorio {
    void guardar(RespuestaTest respuestaTest);
    List<RespuestaTest> obtenerTodos();

    void editarRespuesta(RespuestaTest respuestaTest);
    void eliminarPorId(Long id);

    Optional<RespuestaTest> findById(Long id);

    List<RespuestaTest> obtenerRespuestasPorModeloTestId(Long modeloTestId);
}
