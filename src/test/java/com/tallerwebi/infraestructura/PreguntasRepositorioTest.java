package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ModeloTestRepositorio;
import com.tallerwebi.dominio.PreguntaTest;
import com.tallerwebi.dominio.PreguntaTestRepositorio;
import com.tallerwebi.dominio.ServicioTest;
import com.tallerwebi.presentacion.ControladorTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PreguntasRepositorioTest {
    private ModeloTestRepositorio mockModeloTestRepositorio;
    private PreguntaTestRepositorio mockPreguntaTestRepositorio;
    @Mock
    private ServicioTest mockServicioTest;

    @InjectMocks
    private ControladorTest mockControladorTest;

    @Before
    public void setUp() {
        mockPreguntaTestRepositorio = mock(PreguntaTestRepositorio.class);
        mockModeloTestRepositorio = mock(ModeloTestRepositorio.class);
        mockControladorTest = mock(ControladorTest.class);
        mockServicioTest= mock(ServicioTest.class);

    }
    @BeforeEach
    public void setUp1() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGuardarPregunta() {
        PreguntaTest preguntaTest = new PreguntaTest(); // Crea un objeto PreguntaTest
        preguntaTest.setTexto("¿Cuál es la capital de Francia?");

        mockPreguntaTestRepositorio.guardar(preguntaTest);

        verify(mockPreguntaTestRepositorio, times(1)).guardar(preguntaTest);
    }

    @Test
    public void testObtenerTodos() {
        List<PreguntaTest> expectedPreguntas = Arrays.asList(new PreguntaTest(), new PreguntaTest());

        when(mockPreguntaTestRepositorio.obtenerTodos()).thenReturn(expectedPreguntas);

        List<PreguntaTest> actualPreguntas = mockPreguntaTestRepositorio.obtenerTodos();

        assertEquals(expectedPreguntas, actualPreguntas);
    }

    @Test
    public void testEditarPregunta() {
        // Given
        PreguntaTest pregunta = new PreguntaTest();
        pregunta.setTexto("¿Cuál es la capital de Francia?");

        // When
        String nuevoTexto = "¿Cuál es la capital de España?";
        pregunta.setTexto(nuevoTexto);

        // Then
        assertEquals(nuevoTexto, pregunta.getTexto());
    }
    @Test
    public void testGuardarPregunta2() {
        PreguntaTest pregunta = new PreguntaTest();
        pregunta.setTexto("¿Cuál es el río más largo del mundo?");

        // Ejecuta el método guardar del repositorio
        mockPreguntaTestRepositorio.guardar(pregunta);

        // Verifica que se llamó al método guardar con la pregunta
        verify(mockPreguntaTestRepositorio, times(1)).guardar(pregunta);
    }

    @Test
    public void testEliminarPreguntaExistente() {
        Long idPregunta = 1L;

        mockPreguntaTestRepositorio.eliminarPorId(idPregunta);

        verify(mockPreguntaTestRepositorio, times(1)).eliminarPorId(idPregunta);
    }

    @Test
    public void testEliminarPreguntaPorId() {
        Long idPregunta = 1L;

        mockPreguntaTestRepositorio.eliminarPorId(idPregunta);
    }


    @Test
    public void testObtenerPreguntaPorId() {
        Long idPregunta = 1L;

        Optional<PreguntaTest> actualOptionalPregunta = mockPreguntaTestRepositorio.findById(idPregunta);

        if (actualOptionalPregunta.isPresent()) {
            PreguntaTest actualPregunta = actualOptionalPregunta.get();

        } else {

            System.out.println("No se encontró ninguna pregunta con el ID: " + idPregunta);
        }
    }

    @Test
    public void testExistePreguntaPorId() {
        Long idPregunta = 1L; // Suponiendo que existe una pregunta con este ID

        // Simula que el repositorio devuelve true si la pregunta existe
        when(mockPreguntaTestRepositorio.findById(idPregunta));

       assertEquals(idPregunta, 1L);
    }

    @Test
    public void testGuardarPregunta_ModeloNoExistente_MeArrojeNull() {

        PreguntaTest preguntaTest = new PreguntaTest();
        preguntaTest.setTexto("¿Cuál es la capital de Francia?");

        when(mockServicioTest.obtenerTestPorId(anyLong())).thenReturn(Optional.empty());

        String result = mockControladorTest.guardarPregunta(preguntaTest, 1L);

        assertEquals(null, result);
    }

}