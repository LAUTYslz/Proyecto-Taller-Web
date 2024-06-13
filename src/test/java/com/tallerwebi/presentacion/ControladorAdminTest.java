package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ControladorAdminTest {
    private ServicioProfesional servicioProfesional;
    private ServicioLogin servicioLogin;
    private ServicioAdmi servicioAdmi;
    private ControladorAdministrador controladorAdministrador;

    @BeforeEach
    public void setUp() {
        servicioProfesional = mock(ServicioProfesional.class);
        servicioLogin = mock(ServicioLogin.class);
        servicioAdmi = mock(ServicioAdmi.class);
        controladorAdministrador = new ControladorAdministrador(servicioLogin, servicioAdmi, servicioProfesional);
    }

    @Test
    public void testMostrarFormularioNuevo() {
        // Configurar el comportamiento del mock para los métodos de servicio
        Metodo metodo1 = new Metodo();
        metodo1.setId(1L);
        metodo1.setNombre("Metodo 1");

        TipoProfesional tipo1 = new TipoProfesional();
        tipo1.setId(1L);
        tipo1.setNombre("Tipo 1");

        List<Metodo> metodos = Arrays.asList(metodo1);
        List<TipoProfesional> tipos = Arrays.asList(tipo1);

        when(servicioProfesional.traerTodosLosMetodos()).thenReturn(metodos);
        when(servicioProfesional.traerTodosLosTipos()).thenReturn(tipos);

        // Llamar al método del controlador
        ModelAndView mav = controladorAdministrador.mostrarFormularioNuevo();

        // Verificar la vista y los atributos del modelo
        assertEquals("formulario_crear_profesional", mav.getViewName());
        assertNotNull(mav.getModel().get("profesional"));
        assertEquals(metodos, mav.getModel().get("metodos"));
        assertEquals(tipos, mav.getModel().get("tipos"));
    }

    @Test
    public void testGuardarProfesional() {
        // Crear un objeto Profesional de prueba
        Profesional profesional = new Profesional();
        profesional.setNombre("John Doe");
        profesional.setTelefono("123456789");
        profesional.setEmail("john.doe@example.com");
        profesional.setDireccion("123 Main St");
        profesional.setInstitucion("Instituto XYZ");

        TipoProfesional tipo = new TipoProfesional();
        tipo.setId(1L);
        tipo.setNombre("Tipo 1");

        Metodo metodo = new Metodo();
        metodo.setId(1L);
        metodo.setNombre("Metodo 1");

        profesional.setTipo(tipo);
        profesional.setMetodo(metodo);

        // Configurar el comportamiento del mock para los métodos de servicio
        when(servicioProfesional.guardar(any(Profesional.class))).thenReturn(profesional);

        // Llamar al método del controlador
        String result = controladorAdministrador.agregarProfesional(profesional);

        // Verificar que el método de servicio fue llamado y el resultado de la vista
        verify(servicioProfesional, times(1)).guardar(profesional);
        assertEquals("redirect:/admin/gestionarProfesionales", result);
    }
}
