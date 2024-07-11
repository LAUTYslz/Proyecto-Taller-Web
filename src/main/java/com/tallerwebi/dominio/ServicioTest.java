package com.tallerwebi.dominio;

import java.util.List;
import java.util.Optional;

public interface ServicioTest {

    String calcularResultadoAutismo(ModeloTest respuestasTest);

    String calcularResultadoAsperger(ModeloTest respuestasTest);

    String calcularResultadoTdha(ModeloTest respuestasTest);

    String calcularResultadoDislexia(ModeloTest respuestasTest);

/////////////////////////////////////////

    void crearTest(ModeloTest modeloTest);
    List<ModeloTest> obtenerTodosLosTests();
    void guardarPregunta(PreguntaTest preguntaTest);
    List<PreguntaTest> obtenerTodasLasPreguntas();
    void guardarRespuesta(RespuestaTest respuestaTest);
    List<RespuestaTest> obtenerTodasLasRespuestas();
    Optional<ModeloTest> obtenerTestPorId(Long id);

    void editarTest(ModeloTest modeloTest);

    void editarPregunta(PreguntaTest preguntaTest); // Método para editar Pregunta

    void editarRespuesta(RespuestaTest respuestaTest); // Método para editar Respuesta

    void eliminarTest(Long id);

    void eliminarPregunta(Long id);

    void eliminarRespuesta(Long id);

    Optional<PreguntaTest> obtenerPreguntaPorId(Long id);

    Optional<RespuestaTest> obtenerRespuestaPorId(Long id);

   List<RespuestaTest> obtenerRespuestasPorModeloTestId(Long modeloTestId);

    List<PreguntaTest> obtenerPreguntasPorModeloTestId(Long modeloTestId);
    }


