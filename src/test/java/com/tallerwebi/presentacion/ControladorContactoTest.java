package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Contacto;
import com.tallerwebi.dominio.Metodo;
import com.tallerwebi.dominio.ServicioContacto;
import com.tallerwebi.dominio.TipoContacto;
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

public class ControladorContactoTest {

    private ServicioContacto servicioContacto;
    private ControladorContacto controladorContacto;

    @BeforeEach
    public void setUp() {
        servicioContacto = mock(ServicioContacto.class);
        controladorContacto = new ControladorContacto(servicioContacto);
    }

    @Test
    public void testObtenerContactos(){
        Contacto contacto1 = new Contacto();
        contacto1.setNombre("Dr. Salazar");
        Contacto contacto2 = new Contacto();
        contacto2.setNombre("Dra. López");

        List<Contacto> contactos = new ArrayList<Contacto>();
        contactos.add(contacto1);
        contactos.add(contacto2);

        when(servicioContacto.traerContactos()).thenReturn(contactos);

        List<Contacto> listaObtenida = whenBuscoContactos();

        verify(servicioContacto,times(1)).traerContactos();
        assertThat(listaObtenida.size(),equalTo(2));
    }

    private List<Contacto> whenBuscoContactos() {
        return controladorContacto.obtenerContactos();
    }


    @Test
    public void testObtenerContactosPorMetodo(){
        String nombreMetodo = "DOMAN";
        Contacto contacto1 = new Contacto();
        contacto1.setNombre("Dr. Salazar");
        Contacto contacto2 = new Contacto();
        contacto2.setNombre("Dra. López");

        List<Contacto> contactos = new ArrayList<Contacto>();
        contactos.add(contacto1);
        contactos.add(contacto2);

        when(servicioContacto.traerContactosPorMetodo(nombreMetodo)).thenReturn(contactos);

        List<Contacto> listaObtenida = whenBuscoContactosPorMetodo(nombreMetodo);

        verify(servicioContacto,times(1)).traerContactosPorMetodo(nombreMetodo);
        assertThat(listaObtenida.size(),equalTo(2));
    }

    private List<Contacto> whenBuscoContactosPorMetodo(String nombreMetodo) {
        return controladorContacto.obtenerContactosPorMetodo(nombreMetodo);
    }

    @Test
    public void testObtenerContactosPorMetodo_SinContactos(){
        String nombreMetodo = "DOMAN";

        when(servicioContacto.traerContactosPorMetodo(nombreMetodo)).thenReturn(new ArrayList<>());

        List<Contacto> listaObtenida = whenBuscoContactosPorMetodo(nombreMetodo);

        verify(servicioContacto,times(1)).traerContactosPorMetodo(nombreMetodo);
        assertThat(listaObtenida.size(),equalTo(0));
    }

    @Test
    public void testObtenerContactosPorTipo(){
        String nombreTipo = "Pediatra";
        Contacto contacto1 = new Contacto();
        contacto1.setNombre("Dr. Salazar");
        Contacto contacto2 = new Contacto();
        contacto2.setNombre("Dra. López");

        List<Contacto> contactos = new ArrayList<Contacto>();
        contactos.add(contacto1);
        contactos.add(contacto2);

        when(servicioContacto.traerContactosPorTipo(nombreTipo)).thenReturn(contactos);

        List<Contacto> listaObtenida = whenBuscoContactosPorTipo(nombreTipo);

        verify(servicioContacto,times(1)).traerContactosPorTipo(nombreTipo);
        assertThat(listaObtenida.size(),equalTo(2));
    }

    private List<Contacto> whenBuscoContactosPorTipo(String nombreTipo) {
        return controladorContacto.obtenerContactosPorTipo(nombreTipo);
    }

    @Test
    public void testObtenerContactosPorTipo_SinContactos(){
        String nombreTipo = "Pediatra";

        when(servicioContacto.traerContactosPorTipo(nombreTipo)).thenReturn(new ArrayList<>());

        List<Contacto> listaObtenida = whenBuscoContactosPorTipo(nombreTipo);

        verify(servicioContacto,times(1)).traerContactosPorTipo(nombreTipo);
        assertThat(listaObtenida.size(),equalTo(0));
    }

    @Test
    public void testObtenerContactosPorTipoYMetodo() {
        String nombreTipo = "Pediatra";
        String nombreMetodo = "DOMAN";

        Contacto contacto1 = new Contacto();
        contacto1.setNombre("Dr. Salazar");
        Contacto contacto2 = new Contacto();
        contacto2.setNombre("Dra. López");

        List<Contacto> contactos = new ArrayList<Contacto>();
        contactos.add(contacto1);
        contactos.add(contacto2);

        when(servicioContacto.traerContactosPorTipoYMetodo(nombreTipo, nombreMetodo)).thenReturn(contactos);

        // Act
        List<Contacto> resultado = whenBuscoContactosPorMetodoYTipo(nombreTipo, nombreMetodo);

        // Assert
        assertThat(2,equalTo( resultado.size()));
        verify(servicioContacto, times(1)).traerContactosPorTipoYMetodo(nombreTipo, nombreMetodo);
    }

    private List<Contacto> whenBuscoContactosPorMetodoYTipo(String nombreTipo, String nombreMetodo) {
        return controladorContacto.obtenerContactosPorMetodoYTipo(nombreTipo,nombreMetodo);
    }

    @Test
    public void testObtenerContactosPorTipoYMetodo_SinContactos() {
        String nombreTipo = "Pediatra";
        String nombreMetodo = "Waldorf";

        when(servicioContacto.traerContactosPorTipoYMetodo(nombreTipo, nombreMetodo)).thenReturn(new ArrayList<>());

        List<Contacto> resultado = whenBuscoContactosPorMetodoYTipo(nombreTipo, nombreMetodo);

        assertThat(resultado.size(),equalTo(0));
        verify(servicioContacto, times(1)).traerContactosPorTipoYMetodo(nombreTipo, nombreMetodo);
    }

    @Test
    public void deberiaMostrarTodosLosContactos() {
        List<Contacto> contactos = new ArrayList<>();
        Contacto contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Contacto contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "mlau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Contacto contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "plau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        contactos.add(contactoExistente2);
        contactos.add(contactoExistente3);

        when(servicioContacto.guardar(contactoExistente1)).thenReturn(contactoExistente1);
        when(servicioContacto.guardar(contactoExistente2)).thenReturn(contactoExistente2);
        when(servicioContacto.guardar(contactoExistente3)).thenReturn(contactoExistente3);
        when(servicioContacto.traerContactos()).thenReturn(contactos);

        servicioContacto.guardar(contactoExistente1);
        servicioContacto.guardar(contactoExistente2);
        servicioContacto.guardar(contactoExistente3);

        ModelAndView mav = controladorContacto.mostrarPaginaDeContactos(null,null);
        List<Contacto> result = (List<Contacto>) mav.getModel().get("contactos");

        assertEquals(contactos, result);
        assertEquals("contacto", mav.getViewName());
    }

    private Contacto givenExisteContacto(String nombre, String telefono, String mail, String direccion, String institucion, String nombreMetodo, String nombreTipo) {
        Contacto contacto = new Contacto();
        contacto.setNombre(nombre);
        contacto.setTelefono(telefono);
        contacto.setEmail(mail);
        contacto.setDireccion(direccion);
        contacto.setInstitucion(institucion);
        Metodo metodo = new Metodo();
        metodo.setNombre(nombreMetodo);
        TipoContacto tipo = new TipoContacto();
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
        List<Contacto> contactosFiltrados = new ArrayList<>();
        Contacto contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        contactosFiltrados.add(contactoExistente1);

        when(servicioContacto.guardar(contactoExistente1)).thenReturn(contactoExistente1);
        when(servicioContacto.traerContactosPorTipoYMetodo(nombreTipo, nombreMetodo)).thenReturn(contactosFiltrados);

        servicioContacto.guardar(contactoExistente1);

        ModelAndView mav = controladorContacto.filtrarContactosPorMetodoYTipo(nombreMetodo, nombreTipo);
        List<Contacto> result = (List<Contacto>) mav.getModel().get("ContactosFiltradosPorTipoYMetodo");

        // Assert
        verify(servicioContacto,times(1)).guardar(contactoExistente1);
        verify(servicioContacto,times(1)).traerContactosPorTipoYMetodo(nombreTipo,nombreMetodo);
        assertEquals(contactosFiltrados, result);
        assertEquals("/contacto/filtrarPorMetodoYTipo", mav.getViewName());
    }
}
