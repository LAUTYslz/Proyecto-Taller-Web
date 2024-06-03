package com.tallerwebi.Infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.MetodoNoEncontrado;
import com.tallerwebi.dominio.excepcion.TipoContactoNoEncontrado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class ServicioContactoTest {
    RepositorioContacto repositorioContacto = mock(RepositorioContacto.class);
    RepositorioMetodo repositorioMetodo = mock(RepositorioMetodo.class);
    RepositorioTipoContacto repositorioTipoContacto = mock(RepositorioTipoContacto.class);
    ServicioContacto servicioContacto = new ServicioContactoImpl(repositorioContacto, repositorioMetodo, repositorioTipoContacto);
    @BeforeEach
    public void setUp() {
        repositorioContacto = mock(RepositorioContacto.class);
        repositorioMetodo = mock(RepositorioMetodo.class);
        repositorioTipoContacto = mock(RepositorioTipoContacto.class);
        servicioContacto = new ServicioContactoImpl(repositorioContacto, repositorioMetodo, repositorioTipoContacto);
    }

    @Test
    public void testDatosInicialesCargados() {
        // Arrange
        Metodo metodoWaldorf = new Metodo();
        metodoWaldorf.setNombre("WALDORF");
        TipoContacto tipoPediatra = new TipoContacto();
        tipoPediatra.setNombre("Pediatra");

        when(repositorioMetodo.buscarPorNombre("WALDORF")).thenReturn(metodoWaldorf);
        when(repositorioTipoContacto.buscarPorNombre("Pediatra")).thenReturn(tipoPediatra);

        // Act
        Metodo metodo = repositorioMetodo.buscarPorNombre("WALDORF");
        TipoContacto tipo = repositorioTipoContacto.buscarPorNombre("Pediatra");

        // Assert
        Assertions.assertNotNull(metodo, "El método de especialización 'WALDORF' debería estar cargado en la base de datos");
        Assertions.assertNotNull(tipo, "El tipo de contacto 'Pediatra' debería estar cargado en la base de datos");
    }

    @Test
    public void quePuedaGuardarContacto() {
        givenNoexisteContacto();
        String nombreMetodo = "Montessori";
        String nombreTipoContacto = "Pediatra";
        Contacto contacto = new Contacto();
        contacto.setNombre("Dr salazar");
        contacto.setTelefono("+11223344");
        contacto.setEmail("lau@prueba.com");
        contacto.setDireccion("calle falsa 123");
        contacto.setInstitucion("Hospital General");
        Metodo metodo = new Metodo();
        metodo.setNombre(nombreMetodo);
        TipoContacto tipo = new TipoContacto();
        tipo.setNombre(nombreTipoContacto);

        when(repositorioMetodo.buscarPorNombre(nombreMetodo)).thenReturn(metodo);
        when(repositorioTipoContacto.buscarPorNombre(nombreTipoContacto)).thenReturn(tipo);

        whenGuardoContacto(contacto,nombreMetodo,nombreTipoContacto);

        thenGuardadoContactoExitoso(contacto,nombreMetodo,nombreTipoContacto);
    }

    private void thenGuardadoContactoExitoso(Contacto contacto, String nombreMetodo, String nombreTipoContacto) {
        verify(repositorioMetodo, times(1)).buscarPorNombre(nombreMetodo);
        verify(repositorioTipoContacto,times(1)).buscarPorNombre(nombreTipoContacto);
        verify(repositorioContacto,times(1)).guardar(contacto);
    }

    private void whenGuardoContacto(Contacto contacto, String nombreMetodo, String nombreTipoContacto) {
        servicioContacto.guardarContacto(contacto, nombreMetodo, nombreTipoContacto);
    }

    private void givenNoexisteContacto() {
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

        return servicioContacto.guardar(contacto);
    }

    @Test
    public void queNoPuedaGuardarContactoSiNoExisteMetodo() {
        givenNoexisteContacto();
        String nombreMetodo = "Monte";
        String nombreTipoContacto = "Pediatra";
        Contacto contacto = new Contacto();
        contacto.setNombre("Dr salazar");
        contacto.setTelefono("+11223344");
        contacto.setEmail("lau@prueba.com");
        contacto.setDireccion("calle falsa 123");
        contacto.setInstitucion("Hospital General");

        Metodo metodo = new Metodo();
        metodo.setNombre(nombreMetodo);

        TipoContacto tipo = new TipoContacto();
        tipo.setNombre(nombreTipoContacto);

        when(repositorioTipoContacto.buscarPorNombre(nombreTipoContacto)).thenReturn(tipo);
        when(repositorioMetodo.buscarPorNombre(nombreMetodo)).thenReturn(null);

        assertThrows(MetodoNoEncontrado.class,
                ()->servicioContacto.guardarContacto(contacto, nombreMetodo, nombreTipoContacto));

        verify(repositorioMetodo,times(1)).buscarPorNombre(nombreMetodo);
        verify(repositorioContacto, never()).guardar(contacto);
    }

    @Test
    public void queNoPuedaGuardarContactoSiNoExisteTipoContacto() {
        givenNoexisteContacto();
        String nombreMetodo = "MONTESSORI";
        String nombreTipoContacto = "Doctor";
        Contacto contacto = new Contacto();
        contacto.setNombre("Dr salazar");
        contacto.setTelefono("+11223344");
        contacto.setEmail("lau@prueba.com");
        contacto.setDireccion("calle falsa 123");
        contacto.setInstitucion("Hospital General");

        Metodo metodo = new Metodo();
        metodo.setNombre(nombreMetodo);

        TipoContacto tipo = new TipoContacto();
        tipo.setNombre(nombreTipoContacto);

        when(repositorioTipoContacto.buscarPorNombre(nombreTipoContacto)).thenReturn(null);


        assertThrows(TipoContactoNoEncontrado.class,
                ()->whenGuardoContacto(contacto,nombreMetodo,nombreTipoContacto));

        verify(repositorioTipoContacto,times(1)).buscarPorNombre(nombreTipoContacto);
        verify(repositorioContacto, never()).guardar(contacto);
    }

    @Test
    public void quePuedaModificarContacto() {
        Contacto contactoExistente = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        String email = contactoExistente.getEmail();
        when(repositorioContacto.buscar(email)).thenReturn(contactoExistente);
        contactoExistente.setNombre("Dr Zalazar");
        whenActualizoContacto(contactoExistente);

        thenModificacionExitosa(contactoExistente);    }

    private void thenModificacionExitosa(Contacto contactoExistente) {
        verify(repositorioContacto,times(1)).modificar(contactoExistente);
    }

    private void whenActualizoContacto(Contacto contactoExistente) {
        servicioContacto.actualizarContacto(contactoExistente);
    }

    @Test
    public void quePuedaEliminarContacto() {
        Contacto contactoExistente = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        String email = contactoExistente.getEmail();

        when(repositorioContacto.buscar(email)).thenReturn(contactoExistente);

        whenEliminoContacto(contactoExistente);

        thenEliminacionExitosa(contactoExistente);    }

    private void thenEliminacionExitosa(Contacto contactoExistente) {
        verify(repositorioContacto,times(1)).eliminar(contactoExistente);
    }

    private void whenEliminoContacto(Contacto contactoExistente) {
        servicioContacto.eliminarContacto(contactoExistente);
    }


    @Test
    public void quePuedaObtenerUnaListaDeTodosLosContacto() {
        Contacto contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Contacto contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Contacto contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Pediatra");

        //when(repositorioContacto.buscar(email)).thenReturn(contactoExistente);

        //whenEliminoContacto(contactoExistente);

        //thenEliminacionExitosa(contactoExistente);
        }
/*
    @Test
    public void quePuedaObtenerContactosPorTipoYMetodo() {
        TipoContacto tipoPediatra = new TipoContacto();
        tipoPediatra.setNombre("Pediatra");
        Metodo metodoWaldorf = new Metodo();
        metodoWaldorf.setNombre("Waldorf");

        Contacto contacto1 = new Contacto();
        contacto1.setNombre("Dr. Pérez");
        contacto1.setTipo(tipoPediatra);
        contacto1.setMetodo(metodoWaldorf);

        when(repositorioContacto.traerContactosPorTipoYMetodo(tipoPediatra, metodoWaldorf))
                .thenReturn(Arrays.asList(contacto1));

        List<Contacto> contactos = servicioContacto.obtenerContactosPorTipoYMetodo(tipoPediatra, metodoWaldorf);

        assertThat(contactos.size(), equalTo(1));
        assertThat(contactos.get(0).getNombre(), equalTo("Dr. Pérez"));
    }*/
}
