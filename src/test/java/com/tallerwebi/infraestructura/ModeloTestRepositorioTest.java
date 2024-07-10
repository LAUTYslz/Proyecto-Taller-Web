package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ModeloTestRepositorioTest {

    private ModeloTestRepositorio modeloTestRepositorio;
    private PreguntaTestRepositorio preguntaTestRepositorio;
    private RespuestaTestRepositorio respuestaTestRepositorio;
    private ServicioTestImpl servicioTest;

    @BeforeEach
    public void setUp() {
        modeloTestRepositorio = mock(ModeloTestRepositorio.class);
        preguntaTestRepositorio = mock(PreguntaTestRepositorio.class);
        respuestaTestRepositorio = mock(RespuestaTestRepositorio.class);
        servicioTest = new ServicioTestImpl(modeloTestRepositorio, preguntaTestRepositorio, respuestaTestRepositorio);
    }

    @Test
    public void testCrearTest() {
        ModeloTest modeloTest = new ModeloTest();
        givenNoExisteTest();
        whenGuardoTest(modeloTest);
        thenGuardadoTestExitoso(modeloTest);
    }

    private void thenGuardadoTestExitoso(ModeloTest modeloTest) {
        verify(modeloTestRepositorio, times(1)).guardar(modeloTest);
    }

    private void whenGuardoTest(ModeloTest modeloTest) {
        servicioTest.crearTest(modeloTest);
    }

    private void givenNoExisteTest() {
    }

    @Test
    public void testObtenerTodosLosTests() {
        List<ModeloTest> expectedTests = Arrays.asList(new ModeloTest(), new ModeloTest());
        when(modeloTestRepositorio.obtenerTodos()).thenReturn(expectedTests);

        List<ModeloTest> actualTests = servicioTest.obtenerTodosLosTests();

        assertEquals(expectedTests, actualTests);
        verify(modeloTestRepositorio, times(1)).obtenerTodos();
    }

    @Test
    public void testEditarTest() {
        ModeloTest modeloTest = new ModeloTest();
        givenExisteTest(modeloTest);
        whenEditoTest(modeloTest);
        thenEdicionTestExitosa(modeloTest);
    }

    private void thenEdicionTestExitosa(ModeloTest modeloTest) {
        verify(modeloTestRepositorio, times(1)).guardar(modeloTest);
    }

    private void whenEditoTest(ModeloTest modeloTest) {
        servicioTest.editarTest(modeloTest);
    }

    private void givenExisteTest(ModeloTest modeloTest) {
        when(modeloTestRepositorio.findById(modeloTest.getId())).thenReturn(Optional.of(modeloTest));
    }

    @Test
    public void testEliminarTest() {
        Long id = 1L;
        givenExisteTestConId(id);
        whenEliminoTest(id);
        thenEliminacionTestExitosa(id);
    }

    private void thenEliminacionTestExitosa(Long id) {
        verify(modeloTestRepositorio, times(1)).eliminarPorId(id);
    }

    private void whenEliminoTest(Long id) {
        servicioTest.eliminarTest(id);
    }

    private void givenExisteTestConId(Long id) {
        when(modeloTestRepositorio.findById(id)).thenReturn(Optional.of(new ModeloTest()));
    }

    @Test
    public void testCalcularResultadoAutismo() {
        ModeloTest modeloTest = new ModeloTest();
        modeloTest.setRespuestas(Arrays.asList(
                new RespuestaTest("si"), new RespuestaTest("no"), new RespuestaTest("si")
        ));

        String resultado = servicioTest.calcularResultadoAutismo(modeloTest);

        assertTrue(resultado.contains("No se Detectan Indicios de Autismo."));
    }

    @Test
    public void testCalcularResultadoAsperger() {
        ModeloTest modeloTest = new ModeloTest();
        modeloTest.setRespuestas(Arrays.asList(
                new RespuestaTest("no"), new RespuestaTest("no"), new RespuestaTest("no"), new RespuestaTest("si")
        ));

        String resultado = servicioTest.calcularResultadoAsperger(modeloTest);

        assertTrue(resultado.contains("No se Detectan Indicios de Síndrome de Asperger."));
    }


    @Test
    public void testGuardarPregunta() {
        PreguntaTest preguntaTest = new PreguntaTest();
        givenNoExistePregunta();
        whenGuardoPregunta(preguntaTest);
        thenGuardadoPreguntaExitoso(preguntaTest);
    }

    private void thenGuardadoPreguntaExitoso(PreguntaTest preguntaTest) {
        verify(preguntaTestRepositorio, times(1)).guardar(preguntaTest);
    }

    private void whenGuardoPregunta(PreguntaTest preguntaTest) {
        servicioTest.guardarPregunta(preguntaTest);
    }

    private void givenNoExistePregunta() {
    }

    @Test
    public void testObtenerTodasLasPreguntas() {
        List<PreguntaTest> expectedPreguntas = Arrays.asList(new PreguntaTest(), new PreguntaTest());
        when(preguntaTestRepositorio.obtenerTodos()).thenReturn(expectedPreguntas);

        List<PreguntaTest> actualPreguntas = servicioTest.obtenerTodasLasPreguntas();

        assertEquals(expectedPreguntas, actualPreguntas);
        verify(preguntaTestRepositorio, times(1)).obtenerTodos();
    }

    @Test
    public void testGuardarRespuesta() {
        RespuestaTest respuestaTest = new RespuestaTest();
        givenNoExisteRespuesta();
        whenGuardoRespuesta(respuestaTest);
        thenGuardadoRespuestaExitoso(respuestaTest);
    }

    private void thenGuardadoRespuestaExitoso(RespuestaTest respuestaTest) {
        verify(respuestaTestRepositorio, times(1)).guardar(respuestaTest);
    }

    private void whenGuardoRespuesta(RespuestaTest respuestaTest) {
        servicioTest.guardarRespuesta(respuestaTest);
    }

    private void givenNoExisteRespuesta() {
        // No hay precondiciones para este test
    }

    @Test
    public void testObtenerTodasLasRespuestas() {
        List<RespuestaTest> expectedRespuestas = Arrays.asList(new RespuestaTest(), new RespuestaTest());
        when(respuestaTestRepositorio.obtenerTodos()).thenReturn(expectedRespuestas);

        List<RespuestaTest> actualRespuestas = servicioTest.obtenerTodasLasRespuestas();

        assertEquals(expectedRespuestas, actualRespuestas);
        verify(respuestaTestRepositorio, times(1)).obtenerTodos();
    }
}
