package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
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
    private ServicioMembresiaActivada servicioMembresiaActivada;
    private ControladorAdministrador controladorAdministrador;
    private ServicioPago servicioPago;

    @BeforeEach
    public void setUp() {
        servicioProfesional = mock(ServicioProfesional.class);
        servicioLogin = mock(ServicioLogin.class);
        servicioAdmi = mock(ServicioAdmi.class);
        servicioMembresiaActivada = mock(ServicioMembresiaActivada.class);
        servicioPago = mock(ServicioPago.class);
        controladorAdministrador = new ControladorAdministrador(servicioLogin, servicioAdmi, servicioProfesional, servicioMembresiaActivada,servicioPago );
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
        String nombre = "John Doe";
        String telefono = "123456789";
        String email = "john.doe@example.com";
        String direccion = "123 Main St";
        String institucion = "Instituto XYZ";
        String id = "1l";

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
        ModelAndView result = controladorAdministrador.agregarProfesional(nombre, telefono, email, direccion, institucion, id, id);

        // Verificar que el método de servicio fue llamado y el resultado de la vista
        verify(servicioProfesional, times(1)).guardar(profesional);
        assertEquals("redirect:/admin/gestionarProfesionales", result);
    }
}
