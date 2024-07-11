package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.presentacion.ControladorTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@Component
public class RespuestasTestRepositorioTest {

    @Mock
    private RespuestaTestRepositorio mockRespuestaTestRepositorio;
    @Autowired
    private ModeloTestRepositorio mockModeloTestRepositorio;
    private PreguntaTestRepositorio mockPreguntaTestRepositorio;
    @Mock
    private ServicioTest mockServicioTest;

    private ServicioTestImpl servicioTest;

    @InjectMocks
    private ControladorTest mockControladorTest;

    @BeforeEach
    public void setUp2() {
        MockitoAnnotations.openMocks(this);
    }
    @Before
    public void setUp() {
        mockPreguntaTestRepositorio = mock(PreguntaTestRepositorio.class);
        mockModeloTestRepositorio = mock(ModeloTestRepositorio.class);
        mockControladorTest = mock(ControladorTest.class);
        mockServicioTest = mock(ServicioTest.class);
    }

    @Test
    public void testCrearYGuardarRespuesta() {
        // Crear un ModeloTest simulado
        ModeloTest modeloTest = new ModeloTest();
        modeloTest.setDescripcionTest("Modelo de Prueba");
        modeloTest.setId(1L);
        when(mockServicioTest.obtenerTestPorId(1L)).thenReturn(Optional.of(modeloTest));

        // Crear una RespuestaTest
        RespuestaTest respuestaTest = new RespuestaTest();
        respuestaTest.setTexto("Esta es una respuesta de prueba");
        respuestaTest.setModeloTest(modeloTest);

        // Llamar al método para guardar la respuesta
        mockServicioTest.guardarRespuesta(respuestaTest);

        // Verificar que el método guardarRespuesta fue llamado una vez
        verify(mockServicioTest, times(1)).guardarRespuesta(respuestaTest);


        assertEquals("Esta es una respuesta de prueba", respuestaTest.getTexto());
        assertEquals(modeloTest, respuestaTest.getModeloTest());
    }

    @Test
    public void testObtenerTodasLasRespuestas() {
        // Crear una lista de respuestas de prueba
        RespuestaTest respuesta1 = new RespuestaTest("Respuesta 1");
        RespuestaTest respuesta2 = new RespuestaTest("Respuesta 2");
        List<RespuestaTest> respuestas = Arrays.asList(respuesta1, respuesta2);

        // Configurar el comportamiento del mock
        Mockito.when(mockServicioTest.obtenerTodasLasRespuestas()).thenReturn(respuestas);

        // Llamar al método del servicio
        List<RespuestaTest> resultado = mockServicioTest.obtenerTodasLasRespuestas();

        // Verificar que el resultado no sea nulo y contenga las respuestas esperadas
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Respuesta 1", resultado.get(0).getTexto());
        assertEquals("Respuesta 2", resultado.get(1).getTexto());

        // Verificar que el método del servicio fue llamado una vez
        Mockito.verify(mockServicioTest, times(1)).obtenerTodasLasRespuestas();
    }


    @Test
    public void testObtenerRespuestaPorId() {

        RespuestaTest respuesta = new RespuestaTest("Respuesta 1");

        Mockito.when(mockServicioTest.obtenerRespuestaPorId(anyLong())).thenReturn(Optional.of(respuesta));

        Optional<RespuestaTest> respuestaOpt = mockServicioTest.obtenerRespuestaPorId(1L);

        assertThat(respuestaOpt.isPresent(), is(true));
        assertThat(respuestaOpt.get().getTexto(), is(respuesta.getTexto()));

        Mockito.verify(mockServicioTest, times(1)).obtenerRespuestaPorId(1L);
    }

    @Test
    public void testObtenerRespuestaPorId_NoEncontrado() {

        Mockito.when(mockServicioTest.obtenerRespuestaPorId(anyLong())).thenReturn(Optional.empty());

        Optional<RespuestaTest> respuestaOpt = mockServicioTest.obtenerRespuestaPorId(100L);

        assertThat(respuestaOpt.isPresent(), is(false));

        Mockito.verify(mockServicioTest, times(1)).obtenerRespuestaPorId(100L);
    }

    @Test
    public void testEliminarRespuesta() {

        RespuestaTest respuesta = new RespuestaTest("Respuesta 2");

        Mockito.doNothing().when(mockServicioTest).eliminarRespuesta(anyLong());

        mockServicioTest.eliminarRespuesta(2L);

        Mockito.verify(mockServicioTest, times(1)).eliminarRespuesta(2L);
    }

    @Test
    public void testEditarRespuesta() {
        RespuestaTest respuesta = new RespuestaTest("Respuesta Vieja");

        respuesta.setTexto("Respuesta Nueva");

        Mockito.doNothing().when(mockServicioTest).editarRespuesta(respuesta);

        mockServicioTest.editarRespuesta(respuesta);

        Mockito.verify(mockServicioTest, times(1)).editarRespuesta(respuesta);
    }

    @Test
    public void testObtenerRespuestasPorModeloTestId() {

        ModeloTest modeloTest = new ModeloTest();

        RespuestaTest respuesta1 = new RespuestaTest("Respuesta A");
        respuesta1.setModeloTest(modeloTest);
        RespuestaTest respuesta2 = new RespuestaTest("Respuesta B");
        respuesta2.setModeloTest(modeloTest);

        Mockito.when(mockServicioTest.obtenerRespuestasPorModeloTestId(anyLong()))
                .thenReturn(Arrays.asList(respuesta1, respuesta2));

        List<RespuestaTest> respuestas = mockServicioTest.obtenerRespuestasPorModeloTestId(modeloTest.getId());

        assertThat(respuestas.size(), is(2));
        assertThat(respuestas.contains(respuesta1), is(true));
        assertThat(respuestas.contains(respuesta2), is(true));

        Mockito.verify(mockServicioTest, times(1)).obtenerRespuestasPorModeloTestId(modeloTest.getId());
    }

    @Test
    public void testObtenerRespuestasPorModeloTestId_ConListaVacia() {

        ModeloTest modeloTest = new ModeloTest();

        Mockito.when(mockServicioTest.obtenerRespuestasPorModeloTestId(anyLong()))
                .thenReturn(Collections.emptyList());

        List<RespuestaTest> respuestas = mockServicioTest.obtenerRespuestasPorModeloTestId(modeloTest.getId());

        assertThat(respuestas.isEmpty(), is(true));

        Mockito.verify(mockServicioTest, times(1)).obtenerRespuestasPorModeloTestId(modeloTest.getId());
    }

    @Test
    public void testEditarRespuesta_AsociadaAUnModeloTest() {

        RespuestaTest respuesta = new RespuestaTest("Respuesta Sin Modelo");

        ModeloTest modeloTest = new ModeloTest();

        respuesta.setModeloTest(modeloTest);

        Mockito.doNothing().when(mockServicioTest).editarRespuesta(respuesta);

        mockServicioTest.editarRespuesta(respuesta);

        Mockito.verify(mockServicioTest, times(1)).editarRespuesta(respuesta);

        assertThat(respuesta.getModeloTest(), is(modeloTest));
    }

    //este test verifica que al editar una respuesta puedo asociarlo a un nuevo test o
    //no asociarlo a ninguno necesariamente hasta que se lo vuelva a setear
    @Test
    public void testEditarRespuesta_QueDesasocieAlModeloTestQueSeLaAsocioPrimero() {
        RespuestaTest respuesta = new RespuestaTest("Respuesta Con Modelo");
        ModeloTest modeloTest = new ModeloTest();
        respuesta.setModeloTest(modeloTest);

        respuesta.setModeloTest(null);

        Mockito.doNothing().when(mockServicioTest).editarRespuesta(respuesta);

        mockServicioTest.editarRespuesta(respuesta);

        Mockito.verify(mockServicioTest, times(1)).editarRespuesta(respuesta);

        assertThat(respuesta.getModeloTest(), is(nullValue()));
    }

}