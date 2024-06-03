package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.MetodoNoEncontrado;
import com.tallerwebi.dominio.excepcion.TipoContactoNoEncontrado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

        when(repositorioTipoContacto.buscarPorNombre("Pediatra")).thenReturn(tipoPediatra);
        when(repositorioMetodo.buscarPorNombre("WALDORF")).thenReturn(metodoWaldorf);

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

        when(repositorioTipoContacto.buscarPorNombre(nombreTipoContacto)).thenReturn(tipo);
        when(repositorioMetodo.buscarPorNombre(nombreMetodo)).thenReturn(metodo);

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

    @Test
    public void quePuedaGuardarContactoTiendaSinMetodo() {
        givenNoexisteContacto();
        String nombreMetodo = null;
        String nombreTipoContacto = "Tienda";
        Contacto contacto = new Contacto();
        contacto.setNombre("Mundo Feliz");
        contacto.setTelefono("+11223344");
        contacto.setEmail("lau@prueba.com");
        contacto.setDireccion("calle falsa 123");
        contacto.setInstitucion("Hospital General");
        Metodo metodo = new Metodo();
        metodo.setNombre(nombreMetodo);
        TipoContacto tipo = new TipoContacto();
        tipo.setNombre(nombreTipoContacto);

        when(repositorioTipoContacto.buscarPorNombre(nombreTipoContacto)).thenReturn(tipo);

        whenGuardoContacto(contacto,nombreMetodo,nombreTipoContacto);

        verify(repositorioMetodo,never()).buscarPorNombre(nombreMetodo);
        verify(repositorioTipoContacto,times(1)).buscarPorNombre(nombreTipoContacto);
        verify(repositorioContacto,times(1)).guardar(contacto);
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
        List<Contacto> contactos = new ArrayList<>();
        Contacto contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Contacto contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Contacto contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Pediatra");
        contactos.add(contactoExistente1);
        contactos.add(contactoExistente2);
        contactos.add(contactoExistente3);

        when(repositorioContacto.traerContactos()).thenReturn(contactos);

        List<Contacto> listaContactos = whenBuscoListaDeContactos();

        thenObtengoListaDeContactos(listaContactos);
    }

    private void thenObtengoListaDeContactos(List<Contacto> listaContactos) {
        assertThat(listaContactos.size(),equalTo(3));
        verify(repositorioContacto,times(1)).traerContactos();
    }

    private List<Contacto> whenBuscoListaDeContactos() {
        return servicioContacto.traerContactos();
    }

    @Test
    public void quePuedaObtenerUnaListaDeContactosPorMetodo() {
        List<Contacto> contactos = new ArrayList<>();
        Contacto contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Contacto contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Contacto contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        contactos.add(contactoExistente2);
        Metodo metodo = new Metodo();
        metodo.setNombre("MONTESSORI");

        when(repositorioMetodo.buscarPorNombre("MONTESSORI")).thenReturn(metodo);
        when(repositorioContacto.traerContactosPorMetodo("MONTESSORI")).thenReturn(contactos);

        List<Contacto> listaContactos = whenBuscoListaDeContactosPorMetodo("MONTESSORI");

        thenObtengoListaDeContactosPorMetodo(listaContactos);
    }

    private void thenObtengoListaDeContactosPorMetodo(List<Contacto> listaContactos) {
        assertThat(listaContactos.size(),equalTo(2));
        verify(repositorioContacto,times(1)).traerContactosPorMetodo("MONTESSORI");
    }

    private List<Contacto> whenBuscoListaDeContactosPorMetodo(String nombreMetodo) {
        return servicioContacto.traerContactosPorMetodo(nombreMetodo);
    }

    @Test
    public void queNoPuedaObtenerUnaListaDeContactosPorMetodoSiElMetodoNoExiste() {
        List<Contacto> contactos = new ArrayList<>();
        Contacto contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Contacto contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Contacto contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        contactos.add(contactoExistente2);
        Metodo metodo = new Metodo();
        metodo.setNombre("monte");

        when(repositorioMetodo.buscarPorNombre("monte")).thenReturn(null);

        assertThrows(MetodoNoEncontrado.class,
                ()->whenBuscoListaDeContactosPorMetodo("monte"));
        verify(repositorioContacto,never()).traerContactosPorMetodo("monte");
    }

    @Test
    public void quePuedaObtenerUnaListaDeContactosPorTipo() {
        List<Contacto> contactos = new ArrayList<>();
        Contacto contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Contacto contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Contacto contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        TipoContacto tipo = new TipoContacto();
        tipo.setNombre("Pediatra");

        when(repositorioTipoContacto.buscarPorNombre("Pediatra")).thenReturn(tipo);
        when(repositorioContacto.traerContactosPorTipo("Pediatra")).thenReturn(contactos);

        List<Contacto> listaContactos = whenBuscoListaDeContactosPorTipo("Pediatra");

        thenObtengoListaDeContactosPorTipo(listaContactos);
    }

    private void thenObtengoListaDeContactosPorTipo(List<Contacto> listaContactos) {
        assertThat(listaContactos.size(),equalTo(1));
        verify(repositorioContacto,times(1)).traerContactosPorTipo("Pediatra");
    }

    private List<Contacto> whenBuscoListaDeContactosPorTipo(String nombreTipo) {
        return servicioContacto.traerContactosPorTipo(nombreTipo);
    }


    @Test
    public void queNoPuedaObtenerUnaListaDeContactosPorTipoSiNoExisteElTipo() {
        List<Contacto> contactos = new ArrayList<>();
        Contacto contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Contacto contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Contacto contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        TipoContacto tipo = new TipoContacto();
        tipo.setNombre("Doctor");

        when(repositorioTipoContacto.buscarPorNombre("Doctor")).thenReturn(null);

        assertThrows(TipoContactoNoEncontrado.class,
                ()->whenBuscoListaDeContactosPorTipo("Doctor"));
        verify(repositorioContacto,never()).traerContactosPorTipo("Doctor");
    }

    @Test
    public void quePuedaTraerUnaListaVaciaSiNoHayContactosConElMetodo() {
        List<Contacto> contactos = new ArrayList<>();
        Contacto contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Contacto contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Contacto contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        Metodo metodo = new Metodo();
        metodo.setNombre("DOMAN");

        when(repositorioMetodo.buscarPorNombre("DOMAN")).thenReturn(metodo);
        when(repositorioContacto.traerContactosPorMetodo("DOMAN")).thenReturn(new ArrayList<>());

        List<Contacto> contactosObtenidos = whenBuscoListaDeContactosPorMetodo("DOMAN");

        assertThat(contactosObtenidos.size(),equalTo(0));
        verify(repositorioMetodo,times(1)).buscarPorNombre("DOMAN");
        verify(repositorioContacto,times(1)).traerContactosPorMetodo("DOMAN");
    }

    @Test
    public void quePuedaTraerUnaListaVaciaSiNoHayContactosConTipo() {
        List<Contacto> contactos = new ArrayList<>();
        Contacto contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Contacto contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Contacto contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        TipoContacto tipo = new TipoContacto();
        tipo.setNombre("Psicopedagogo");

        when(repositorioTipoContacto.buscarPorNombre("Psicopedagogo")).thenReturn(tipo);
        when(repositorioContacto.traerContactosPorTipo("Psicopedagogo")).thenReturn(new ArrayList<>());

        List<Contacto> contactosObtenidos = whenBuscoListaDeContactosPorTipo("Psicopedagogo");

        assertThat(contactosObtenidos.size(),equalTo(0));
        verify(repositorioTipoContacto,times(1)).buscarPorNombre("Psicopedagogo");
        verify(repositorioContacto,times(1)).traerContactosPorTipo("Psicopedagogo");
    }

    @Test
    public void quePuedaBuscarUnaListaBuscandoPorMetodoYTipo() {
        List<Contacto> contactos = new ArrayList<>();
        Contacto contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Contacto contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Contacto contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        TipoContacto tipo = new TipoContacto();
        tipo.setNombre("Pediatra");
        Metodo metodo = new Metodo();
        metodo.setNombre("MONTESSORI");

        when(repositorioTipoContacto.buscarPorNombre("Pediatra")).thenReturn(tipo);
        when(repositorioMetodo.buscarPorNombre("MONTESSORI")).thenReturn(metodo);
        when(repositorioContacto.traerContactosPorTipoYMetodo("Pediatra","MONTESSORI")).thenReturn(contactos);

        List<Contacto> contactosObtenidos = whenBuscoListaDeContactosPorTipoYMetodo("Pediatra", "MONTESSORI");

        assertThat(contactosObtenidos.size(),equalTo(1));
        verify(repositorioMetodo,times(1)).buscarPorNombre("MONTESSORI");
        verify(repositorioTipoContacto,times(1)).buscarPorNombre("Pediatra");
        verify(repositorioContacto,times(1)).traerContactosPorTipoYMetodo("Pediatra","MONTESSORI");
    }

    private List<Contacto> whenBuscoListaDeContactosPorTipoYMetodo(String nombreTipo, String nombreMetodo) {
        return servicioContacto.traerContactosPorTipoYMetodo(nombreTipo,nombreMetodo);
    }

    @Test
    public void testBuscarContactosPorTipoYMetodoSinContactos() {
        List<Contacto> contactos = new ArrayList<>();
        Contacto contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Contacto contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Contacto contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        TipoContacto tipo = new TipoContacto();
        tipo.setNombre("Pediatra");
        Metodo metodo = new Metodo();
        metodo.setNombre("DOMAN");

        when(repositorioMetodo.buscarPorNombre("DOMAN")).thenReturn(metodo);
        when(repositorioTipoContacto.buscarPorNombre("Pediatra")).thenReturn(tipo);
        when(repositorioContacto.traerContactosPorTipoYMetodo("Pediatra", "DOMAN")).thenReturn(new ArrayList<>());

        // Act
        List<Contacto> contactosObtenidos = servicioContacto.traerContactosPorTipoYMetodo("Pediatra", "DOMAN");

        // Assert
        assertThat(contactosObtenidos.size(), equalTo(0));
        verify(repositorioMetodo, times(1)).buscarPorNombre("DOMAN");
        verify(repositorioTipoContacto, times(1)).buscarPorNombre("Pediatra");
        verify(repositorioContacto, times(1)).traerContactosPorTipoYMetodo("Pediatra", "DOMAN");
    }

}
