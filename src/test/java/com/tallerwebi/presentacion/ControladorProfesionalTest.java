package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Profesional;
import com.tallerwebi.dominio.Metodo;
import com.tallerwebi.dominio.ServicioProfesional;
import com.tallerwebi.dominio.TipoProfesional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ControladorProfesionalTest {

    private ServicioProfesional servicioProfesional;
    private ControladorProfesional controladorProfesional;

    @BeforeEach
    public void setUp() {
        servicioProfesional = mock(ServicioProfesional.class);
        controladorProfesional = new ControladorProfesional(servicioProfesional);
    }

    @Test
    public void testObtenerProfesionales(){
        Profesional contacto1 = new Profesional();
        contacto1.setNombre("Dr. Salazar");
        Profesional contacto2 = new Profesional();
        contacto2.setNombre("Dra. López");

        List<Profesional> contactos = new ArrayList<Profesional>();
        contactos.add(contacto1);
        contactos.add(contacto2);

        when(servicioProfesional.traerProfesionales()).thenReturn(contactos);

        List<Profesional> listaObtenida = whenBuscoContactos();

        verify(servicioProfesional,times(1)).traerProfesionales();
        assertThat(listaObtenida.size(),equalTo(2));
    }

    private List<Profesional> whenBuscoContactos() {
        return controladorProfesional.obtenerProfesionales();
    }


    @Test
    public void testObtenerContactosPorMetodo(){
        String nombreMetodo = "DOMAN";
        Profesional contacto1 = new Profesional();
        contacto1.setNombre("Dr. Salazar");
        Profesional contacto2 = new Profesional();
        contacto2.setNombre("Dra. López");

        List<Profesional> contactos = new ArrayList<Profesional>();
        contactos.add(contacto1);
        contactos.add(contacto2);

        when(servicioProfesional.traerProfesionalesPorMetodo(nombreMetodo)).thenReturn(contactos);

        List<Profesional> listaObtenida = whenBuscoContactosPorMetodo(nombreMetodo);

        verify(servicioProfesional,times(1)).traerProfesionalesPorMetodo(nombreMetodo);
        assertThat(listaObtenida.size(),equalTo(2));
    }

    private List<Profesional> whenBuscoContactosPorMetodo(String nombreMetodo) {
        return controladorProfesional.obtenerProfesionalesPorMetodo(nombreMetodo);
    }

    @Test
    public void testObtenerContactosPorMetodo_SinContactos(){
        String nombreMetodo = "DOMAN";

        when(servicioProfesional.traerProfesionalesPorMetodo(nombreMetodo)).thenReturn(new ArrayList<>());

        List<Profesional> listaObtenida = whenBuscoContactosPorMetodo(nombreMetodo);

        verify(servicioProfesional,times(1)).traerProfesionalesPorMetodo(nombreMetodo);
        assertThat(listaObtenida.size(),equalTo(0));
    }

    @Test
    public void testObtenerContactosPorTipo(){
        String nombreTipo = "Pediatra";
        Profesional contacto1 = new Profesional();
        contacto1.setNombre("Dr. Salazar");
        Profesional contacto2 = new Profesional();
        contacto2.setNombre("Dra. López");

        List<Profesional> contactos = new ArrayList<Profesional>();
        contactos.add(contacto1);
        contactos.add(contacto2);

        when(servicioProfesional.traerProfesionalesPorTipo(nombreTipo)).thenReturn(contactos);

        List<Profesional> listaObtenida = whenBuscoContactosPorTipo(nombreTipo);

        verify(servicioProfesional,times(1)).traerProfesionalesPorTipo(nombreTipo);
        assertThat(listaObtenida.size(),equalTo(2));
    }

    private List<Profesional> whenBuscoContactosPorTipo(String nombreTipo) {
        return controladorProfesional.obtenerProfesionalesPorTipo(nombreTipo);
    }

    @Test
    public void testObtenerContactosPorTipo_SinContactos(){
        String nombreTipo = "Pediatra";

        when(servicioProfesional.traerProfesionalesPorTipo(nombreTipo)).thenReturn(new ArrayList<>());

        List<Profesional> listaObtenida = whenBuscoContactosPorTipo(nombreTipo);

        verify(servicioProfesional,times(1)).traerProfesionalesPorTipo(nombreTipo);
        assertThat(listaObtenida.size(),equalTo(0));
    }

    @Test
    public void testObtenerContactosPorTipoYMetodo() {
        String nombreTipo = "Pediatra";
        String nombreMetodo = "DOMAN";

        Profesional contacto1 = new Profesional();
        contacto1.setNombre("Dr. Salazar");
        Profesional contacto2 = new Profesional();
        contacto2.setNombre("Dra. López");

        List<Profesional> contactos = new ArrayList<Profesional>();
        contactos.add(contacto1);
        contactos.add(contacto2);

        when(servicioProfesional.traerProfesionalesPorTipoYMetodo(nombreTipo, nombreMetodo)).thenReturn(contactos);

        // Act
        List<Profesional> resultado = whenBuscoContactosPorMetodoYTipo(nombreTipo, nombreMetodo);

        // Assert
        assertThat(2,equalTo( resultado.size()));
        verify(servicioProfesional, times(1)).traerProfesionalesPorTipoYMetodo(nombreTipo, nombreMetodo);
    }

    private List<Profesional> whenBuscoContactosPorMetodoYTipo(String nombreTipo, String nombreMetodo) {
        return controladorProfesional.obtenerProfesionalesPorMetodoYTipo(nombreTipo,nombreMetodo);
    }

    @Test
    public void testObtenerContactosPorTipoYMetodo_SinContactos() {
        String nombreTipo = "Pediatra";
        String nombreMetodo = "Waldorf";

        when(servicioProfesional.traerProfesionalesPorTipoYMetodo(nombreTipo, nombreMetodo)).thenReturn(new ArrayList<>());

        List<Profesional> resultado = whenBuscoContactosPorMetodoYTipo(nombreTipo, nombreMetodo);

        assertThat(resultado.size(),equalTo(0));
        verify(servicioProfesional, times(1)).traerProfesionalesPorTipoYMetodo(nombreTipo, nombreMetodo);
    }

    @Test
    public void deberiaMostrarTodosLosContactos() {
        List<Profesional> contactos = new ArrayList<>();
        Profesional contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Profesional contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "mlau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Profesional contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "plau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        contactos.add(contactoExistente2);
        contactos.add(contactoExistente3);

        when(servicioProfesional.guardar(contactoExistente1)).thenReturn(contactoExistente1);
        when(servicioProfesional.guardar(contactoExistente2)).thenReturn(contactoExistente2);
        when(servicioProfesional.guardar(contactoExistente3)).thenReturn(contactoExistente3);
        when(servicioProfesional.traerProfesionalesPorTipoYMetodo("","")).thenReturn(contactos);

        servicioProfesional.guardar(contactoExistente1);
        servicioProfesional.guardar(contactoExistente2);
        servicioProfesional.guardar(contactoExistente3);

        ModelAndView mav = controladorProfesional.mostrarPaginaProfesionales("","");
        List<Profesional> result = (List<Profesional>) mav.getModel().get("profesionales");

        assertEquals(contactos, result);
        assertEquals("profesional", mav.getViewName());
    }

    private Profesional givenExisteContacto(String nombre, String telefono, String mail, String direccion, String institucion, String nombreMetodo, String nombreTipo) {
        Profesional contacto = new Profesional();
        contacto.setNombre(nombre);
        contacto.setTelefono(telefono);
        contacto.setEmail(mail);
        contacto.setDireccion(direccion);
        contacto.setInstitucion(institucion);
        Metodo metodo = new Metodo();
        metodo.setNombre(nombreMetodo);
        TipoProfesional tipo = new TipoProfesional();
        tipo.setNombre(nombreTipo);
        contacto.setTipo(tipo);
        contacto.setMetodo(metodo);

        return contacto;
    }
/*
    @Test
    public void deberiaFiltrarContactosPorMetodo() {
        String nombreMetodo = "MONTESSORI";
        List<Contacto> contactosFiltrados = new ArrayList<>();
        Contacto contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Contacto contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "mlau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Contacto contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "plau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactosFiltrados.add(contactoExistente1);
        contactosFiltrados.add(contactoExistente2);

        when(servicioContacto.guardar(contactoExistente1)).thenReturn(contactoExistente1);
        when(servicioContacto.guardar(contactoExistente2)).thenReturn(contactoExistente2);
        when(servicioContacto.traerContactosPorMetodo(nombreMetodo)).thenReturn(contactosFiltrados);

        servicioContacto.guardar(contactoExistente1);
        servicioContacto.guardar(contactoExistente2);

        ModelAndView mav = controladorContacto.filtrarContactosPorMetodo(nombreMetodo);
        List<Contacto> result = (List<Contacto>) mav.getModel().get("ContactosFiltradosPorMetodo");

        verify(servicioContacto,times(1)).guardar(contactoExistente1);
        assertEquals(contactosFiltrados, result);
        assertEquals("contacto/filtrarPorMetodo", mav.getViewName());
    }


    @Test
    public void deberiaFiltrarContactosPorTipo() {
        String nombreTipo = "Pediatra";
        List<Contacto> contactosFiltrados = new ArrayList<>();
        Contacto contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Contacto contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "mlau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Contacto contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "plau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactosFiltrados.add(contactoExistente1);

        when(servicioContacto.guardar(contactoExistente1)).thenReturn(contactoExistente1);
        when(servicioContacto.traerContactosPorTipo(nombreTipo)).thenReturn(contactosFiltrados);

        servicioContacto.guardar(contactoExistente1);

        ModelAndView mav = controladorContacto.filtrarContactosPorTipo(nombreTipo);
        List<Contacto> result = (List<Contacto>) mav.getModel().get("ContactosFiltradosPorTipo");

        verify(servicioContacto,times(1)).guardar(contactoExistente1);
        assertEquals(contactosFiltrados, result);
        assertEquals("contacto/filtrarPorTipo", mav.getViewName());
    }
 */
    @Test
    public void deberiaFiltrarContactosPorMetodoYTipo() {
        // Arrange
        String nombreMetodo = "MONTESSORI";
        String nombreTipo = "Pediatra";
        List<Profesional> contactosFiltrados = new ArrayList<>();
        Profesional contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        contactosFiltrados.add(contactoExistente1);

        when(servicioProfesional.guardar(contactoExistente1)).thenReturn(contactoExistente1);
        when(servicioProfesional.traerProfesionalesPorTipoYMetodo(nombreTipo, nombreMetodo)).thenReturn(contactosFiltrados);

        servicioProfesional.guardar(contactoExistente1);

        ModelAndView mav = controladorProfesional.filtrarContactosPorMetodoYTipo(nombreMetodo, nombreTipo);
        List<Profesional> result = (List<Profesional>) mav.getModel().get("ContactosFiltradosPorTipoYMetodo");

        // Assert
        verify(servicioProfesional,times(1)).guardar(contactoExistente1);
        verify(servicioProfesional,times(1)).traerProfesionalesPorTipoYMetodo(nombreTipo,nombreMetodo);
        assertEquals(contactosFiltrados, result);
        assertEquals("/profesional/filtrarPorMetodoYTipo", mav.getViewName());
    }
}
