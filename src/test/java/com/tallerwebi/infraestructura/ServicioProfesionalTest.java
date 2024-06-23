package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.MetodoNoEncontrado;
import com.tallerwebi.dominio.excepcion.NoSeEncontraronProfesionalesEnLaBusqueda;
import com.tallerwebi.dominio.excepcion.TipoProfesionalNoEncontrado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class ServicioProfesionalTest {
    RepositorioProfesional repositorioContacto = mock(RepositorioProfesional.class);
    RepositorioMetodo repositorioMetodo = mock(RepositorioMetodo.class);
    RepositorioTipoProfesional repositorioTipoContacto = mock(RepositorioTipoProfesional.class);
    ServicioProfesional servicioContacto = new ServicioProfesionalImpl(repositorioContacto, repositorioMetodo, repositorioTipoContacto);
    @BeforeEach
    public void setUp() {
        repositorioContacto = mock(RepositorioProfesional.class);
        repositorioMetodo = mock(RepositorioMetodo.class);
        repositorioTipoContacto = mock(RepositorioTipoProfesional.class);
        servicioContacto = new ServicioProfesionalImpl(repositorioContacto, repositorioMetodo, repositorioTipoContacto);
    }

    @Test
    public void testDatosInicialesCargados() {
        // Arrange
        Metodo metodoWaldorf = new Metodo();
        metodoWaldorf.setNombre("WALDORF");
        TipoProfesional tipoPediatra = new TipoProfesional();
        tipoPediatra.setNombre("Pediatra");

        when(repositorioTipoContacto.buscarPorNombreDeTipo("Pediatra")).thenReturn(tipoPediatra);
        when(repositorioMetodo.buscarPorNombreDeMetodo("WALDORF")).thenReturn(metodoWaldorf);

        // Act
        Metodo metodo = repositorioMetodo.buscarPorNombreDeMetodo("WALDORF");
        TipoProfesional tipo = repositorioTipoContacto.buscarPorNombreDeTipo("Pediatra");

        // Assert
        Assertions.assertNotNull(metodo, "El método de especialización 'WALDORF' debería estar cargado en la base de datos");
        Assertions.assertNotNull(tipo, "El tipo de contacto 'Pediatra' debería estar cargado en la base de datos");
    }

    @Test
    public void quePuedaGuardarContacto() {
        givenNoexisteContacto();
        String nombreMetodo = "Montessori";
        String nombreTipoContacto = "Pediatra";
        Profesional contacto = new Profesional();
        contacto.setNombre("Dr salazar");
        contacto.setTelefono("+11223344");
        contacto.setEmail("lau@prueba.com");
        contacto.setDireccion("calle falsa 123");
        contacto.setInstitucion("Hospital General");
        Metodo metodo = new Metodo();
        metodo.setNombre(nombreMetodo);
        TipoProfesional tipo = new TipoProfesional();
        tipo.setNombre(nombreTipoContacto);

        when(repositorioTipoContacto.buscarPorNombreDeTipo(nombreTipoContacto)).thenReturn(tipo);
        when(repositorioMetodo.buscarPorNombreDeMetodo(nombreMetodo)).thenReturn(metodo);

        whenGuardoContacto(contacto,nombreMetodo,nombreTipoContacto);

        thenGuardadoContactoExitoso(contacto,nombreMetodo,nombreTipoContacto);
    }

    private void thenGuardadoContactoExitoso(Profesional contacto, String nombreMetodo, String nombreTipoContacto) {
        verify(repositorioMetodo, times(1)).buscarPorNombreDeMetodo(nombreMetodo);
        verify(repositorioTipoContacto,times(1)).buscarPorNombreDeTipo(nombreTipoContacto);
        verify(repositorioContacto,times(1)).guardar(contacto);
    }

    private void whenGuardoContacto(Profesional contacto, String nombreMetodo, String nombreTipoContacto) {
        servicioContacto.guardarProfesional(contacto, nombreMetodo, nombreTipoContacto);
    }

    private void givenNoexisteContacto() {
    }

    @Test
    public void quePuedaGuardarContactoTiendaSinMetodo() {
        givenNoexisteContacto();
        String nombreMetodo = null;
        String nombreTipoContacto = "Tienda";
        Profesional contacto = new Profesional();
        contacto.setNombre("Mundo Feliz");
        contacto.setTelefono("+11223344");
        contacto.setEmail("lau@prueba.com");
        contacto.setDireccion("calle falsa 123");
        contacto.setInstitucion("Hospital General");
        Metodo metodo = new Metodo();
        metodo.setNombre(nombreMetodo);
        TipoProfesional tipo = new TipoProfesional();
        tipo.setNombre(nombreTipoContacto);

        when(repositorioTipoContacto.buscarPorNombreDeTipo(nombreTipoContacto)).thenReturn(tipo);

        whenGuardoContacto(contacto,nombreMetodo,nombreTipoContacto);

        verify(repositorioMetodo,never()).buscarPorNombreDeMetodo(nombreMetodo);
        verify(repositorioTipoContacto,times(1)).buscarPorNombreDeTipo(nombreTipoContacto);
        verify(repositorioContacto,times(1)).guardar(contacto);
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

        return servicioContacto.guardar(contacto);
    }

    @Test
    public void queNoPuedaGuardarContactoSiNoExisteMetodo() {
        givenNoexisteContacto();
        String nombreMetodo = "Monte";
        String nombreTipoContacto = "Pediatra";
        Profesional contacto = new Profesional();
        contacto.setNombre("Dr salazar");
        contacto.setTelefono("+11223344");
        contacto.setEmail("lau@prueba.com");
        contacto.setDireccion("calle falsa 123");
        contacto.setInstitucion("Hospital General");

        Metodo metodo = new Metodo();
        metodo.setNombre(nombreMetodo);

        TipoProfesional tipo = new TipoProfesional();
        tipo.setNombre(nombreTipoContacto);

        when(repositorioTipoContacto.buscarPorNombreDeTipo(nombreTipoContacto)).thenReturn(tipo);
        when(repositorioMetodo.buscarPorNombreDeMetodo(nombreMetodo)).thenReturn(null);

        assertThrows(MetodoNoEncontrado.class,
                ()->servicioContacto.guardarProfesional(contacto, nombreMetodo, nombreTipoContacto));

        verify(repositorioMetodo,times(1)).buscarPorNombreDeMetodo(nombreMetodo);
        verify(repositorioContacto, never()).guardar(contacto);
    }

    @Test
    public void queNoPuedaGuardarContactoSiNoExisteTipoContacto() {
        givenNoexisteContacto();
        String nombreMetodo = "MONTESSORI";
        String nombreTipoContacto = "Doctor";
        Profesional contacto = new Profesional();
        contacto.setNombre("Dr salazar");
        contacto.setTelefono("+11223344");
        contacto.setEmail("lau@prueba.com");
        contacto.setDireccion("calle falsa 123");
        contacto.setInstitucion("Hospital General");

        Metodo metodo = new Metodo();
        metodo.setNombre(nombreMetodo);

        TipoProfesional tipo = new TipoProfesional();
        tipo.setNombre(nombreTipoContacto);

        when(repositorioTipoContacto.buscarPorNombreDeTipo(nombreTipoContacto)).thenReturn(null);


        assertThrows(TipoProfesionalNoEncontrado.class,
                ()->whenGuardoContacto(contacto,nombreMetodo,nombreTipoContacto));

        verify(repositorioTipoContacto,times(1)).buscarPorNombreDeTipo(nombreTipoContacto);
        verify(repositorioContacto, never()).guardar(contacto);
    }
/*
    @Test
    public void quePuedaModificarContacto() {
        Profesional contactoExistente = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        String email = contactoExistente.getEmail();
        when(repositorioContacto.buscar(email)).thenReturn(contactoExistente);
        contactoExistente.setNombre("Dr Zalazar");
        whenActualizoContacto(contactoExistente);

        thenModificacionExitosa(contactoExistente);    }

    private void thenModificacionExitosa(Profesional contactoExistente) {
        verify(repositorioContacto,times(1)).modificar(contactoExistente);
    }

    private void whenActualizoContacto(Profesional contactoExistente) {
        servicioContacto.actualizarProfesional(contactoExistente);
    }*/

    @Test
    public void quePuedaEliminarContacto() {
        Profesional contactoExistente = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        String email = contactoExistente.getEmail();

        when(repositorioContacto.buscar(email)).thenReturn(contactoExistente);

        whenEliminoContacto(contactoExistente);

        thenEliminacionExitosa(contactoExistente);    }

    private void thenEliminacionExitosa(Profesional contactoExistente) {
        verify(repositorioContacto,times(1)).eliminar(contactoExistente);
    }

    private void whenEliminoContacto(Profesional contactoExistente) {
        servicioContacto.eliminarProfesional(contactoExistente);
    }


    @Test
    public void quePuedaObtenerUnaListaDeTodosLosContacto() {
        List<Profesional> contactos = new ArrayList<>();
        Profesional contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Profesional contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Profesional contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Pediatra");
        contactos.add(contactoExistente1);
        contactos.add(contactoExistente2);
        contactos.add(contactoExistente3);

        when(repositorioContacto.traerProfesionales()).thenReturn(contactos);

        List<Profesional> listaContactos = whenBuscoListaDeContactos();

        thenObtengoListaDeContactos(listaContactos);
    }

    private void thenObtengoListaDeContactos(List<Profesional> listaContactos) {
        assertThat(listaContactos.size(),equalTo(3));
        verify(repositorioContacto,times(1)).traerProfesionales();
    }

    private List<Profesional> whenBuscoListaDeContactos() {
        return servicioContacto.traerProfesionales();
    }

    @Test
    public void quePuedaObtenerUnaListaDeContactosPorMetodo() {
        List<Profesional> contactos = new ArrayList<>();
        Profesional contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Profesional contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Profesional contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        contactos.add(contactoExistente2);
        Metodo metodo = new Metodo();
        metodo.setNombre("MONTESSORI");

        when(repositorioMetodo.buscarPorNombreDeMetodo("MONTESSORI")).thenReturn(metodo);
        when(repositorioContacto.traerProfesionalesPorMetodo("MONTESSORI")).thenReturn(contactos);

        List<Profesional> listaContactos = whenBuscoListaDeContactosPorMetodo("MONTESSORI");

        thenObtengoListaDeContactosPorMetodo(listaContactos);
    }

    private void thenObtengoListaDeContactosPorMetodo(List<Profesional> listaContactos) {
        assertThat(listaContactos.size(),equalTo(2));
        verify(repositorioContacto,times(1)).traerProfesionalesPorMetodo("MONTESSORI");
    }

    private List<Profesional> whenBuscoListaDeContactosPorMetodo(String nombreMetodo) {
        return servicioContacto.traerProfesionalesPorMetodo(nombreMetodo);
    }

    @Test
    public void queNoPuedaObtenerUnaListaDeContactosPorMetodoSiElMetodoNoExiste() {
        List<Profesional> contactos = new ArrayList<>();
        Profesional contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Profesional contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Profesional contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        contactos.add(contactoExistente2);
        Metodo metodo = new Metodo();
        metodo.setNombre("monte");

        when(repositorioMetodo.buscarPorNombreDeMetodo("monte")).thenReturn(null);

        assertThrows(MetodoNoEncontrado.class,
                ()->whenBuscoListaDeContactosPorMetodo("monte"));
        verify(repositorioContacto,never()).traerProfesionalesPorMetodo("monte");
    }

    @Test
    public void quePuedaObtenerUnaListaDeContactosPorTipo() {
        List<Profesional> contactos = new ArrayList<>();
        Profesional contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Profesional contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Profesional contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        TipoProfesional tipo = new TipoProfesional();
        tipo.setNombre("Pediatra");

        when(repositorioTipoContacto.buscarPorNombreDeTipo("Pediatra")).thenReturn(tipo);
        when(repositorioContacto.traerProfesionalesPorTipo("Pediatra")).thenReturn(contactos);

        List<Profesional> listaContactos = whenBuscoListaDeContactosPorTipo("Pediatra");

        thenObtengoListaDeContactosPorTipo(listaContactos);
    }

    private void thenObtengoListaDeContactosPorTipo(List<Profesional> listaContactos) {
        assertThat(listaContactos.size(),equalTo(1));
        verify(repositorioContacto,times(1)).traerProfesionalesPorTipo("Pediatra");
    }

    private List<Profesional> whenBuscoListaDeContactosPorTipo(String nombreTipo) {
        return servicioContacto.traerProfesionalesPorTipo(nombreTipo);
    }


    @Test
    public void queNoPuedaObtenerUnaListaDeContactosPorTipoSiNoExisteElTipo() {
        List<Profesional> contactos = new ArrayList<>();
        Profesional contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Profesional contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Profesional contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        TipoProfesional tipo = new TipoProfesional();
        tipo.setNombre("Doctor");

        when(repositorioTipoContacto.buscarPorNombreDeTipo("Doctor")).thenReturn(null);

        assertThrows(TipoProfesionalNoEncontrado.class,
                ()->whenBuscoListaDeContactosPorTipo("Doctor"));
        verify(repositorioContacto,never()).traerProfesionalesPorTipo("Doctor");
    }

    @Test
    public void quePuedaTraerUnaListaVaciaSiNoHayContactosConElMetodo() {
        List<Profesional> contactos = new ArrayList<>();
        Profesional contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Profesional contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Profesional contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        Metodo metodo = new Metodo();
        metodo.setNombre("DOMAN");

        when(repositorioMetodo.buscarPorNombreDeMetodo("DOMAN")).thenReturn(metodo);
        when(repositorioContacto.traerProfesionalesPorMetodo("DOMAN")).thenReturn(new ArrayList<>());

        List<Profesional> contactosObtenidos = whenBuscoListaDeContactosPorMetodo("DOMAN");

        assertThat(contactosObtenidos.size(),equalTo(0));
        verify(repositorioMetodo,times(1)).buscarPorNombreDeMetodo("DOMAN");
        verify(repositorioContacto,times(1)).traerProfesionalesPorMetodo("DOMAN");
    }

    @Test
    public void quePuedaTraerUnaListaVaciaSiNoHayContactosConTipo() {
        List<Profesional> contactos = new ArrayList<>();
        Profesional contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Profesional contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Profesional contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        TipoProfesional tipo = new TipoProfesional();
        tipo.setNombre("Psicopedagogo");

        when(repositorioTipoContacto.buscarPorNombreDeTipo("Psicopedagogo")).thenReturn(tipo);
        when(repositorioContacto.traerProfesionalesPorTipo("Psicopedagogo")).thenReturn(new ArrayList<>());

        List<Profesional> contactosObtenidos = whenBuscoListaDeContactosPorTipo("Psicopedagogo");

        assertThat(contactosObtenidos.size(),equalTo(0));
        verify(repositorioTipoContacto,times(1)).buscarPorNombreDeTipo("Psicopedagogo");
        verify(repositorioContacto,times(1)).traerProfesionalesPorTipo("Psicopedagogo");
    }

    @Test
    public void quePuedaBuscarUnaListaBuscandoPorMetodoYTipo() {
        List<Profesional> contactos = new ArrayList<>();
        Profesional contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Profesional contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Profesional contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        TipoProfesional tipo = new TipoProfesional();
        tipo.setNombre("Pediatra");
        Metodo metodo = new Metodo();
        metodo.setNombre("MONTESSORI");

        when(repositorioTipoContacto.buscarPorNombreDeTipo("Pediatra")).thenReturn(tipo);
        when(repositorioMetodo.buscarPorNombreDeMetodo("MONTESSORI")).thenReturn(metodo);
        when(repositorioContacto.traerProfesionalesPorTipoYMetodo("Pediatra","MONTESSORI")).thenReturn(contactos);

        List<Profesional> contactosObtenidos = whenBuscoListaDeContactosPorTipoYMetodo("Pediatra", "MONTESSORI");

        assertThat(contactosObtenidos.size(),equalTo(1));
        verify(repositorioMetodo,times(1)).buscarPorNombreDeMetodo("MONTESSORI");
        verify(repositorioTipoContacto,times(1)).buscarPorNombreDeTipo("Pediatra");
        verify(repositorioContacto,times(1)).traerProfesionalesPorTipoYMetodo("Pediatra","MONTESSORI");
    }

    private List<Profesional> whenBuscoListaDeContactosPorTipoYMetodo(String nombreTipo, String nombreMetodo) {
        return servicioContacto.traerProfesionalesPorTipoYMetodo(nombreTipo,nombreMetodo);
    }

    @Test
    public void testBuscarContactosPorTipoYMetodoSinContactos() {
        List<Profesional> contactos = new ArrayList<>();
        Profesional contactoExistente1 = givenExisteContacto("Dr salazar", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Pediatra");
        Profesional contactoExistente2 = givenExisteContacto("Dr Pereira", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "MONTESSORI", "Obstetra");
        Profesional contactoExistente3 = givenExisteContacto("Tienda Feliz", "+11223344", "lau@prueba.com", "calle falsa 123", "Hospital General", "Nulo", "Tienda");
        contactos.add(contactoExistente1);
        TipoProfesional tipo = new TipoProfesional();
        tipo.setNombre("Pediatra");
        Metodo metodo = new Metodo();
        metodo.setNombre("DOMAN");

        when(repositorioMetodo.buscarPorNombreDeMetodo("DOMAN")).thenReturn(metodo);
        when(repositorioTipoContacto.buscarPorNombreDeTipo("Pediatra")).thenReturn(tipo);
        //when(repositorioContacto.traerContactosPorTipoYMetodo("Pediatra", "DOMAN")).thenReturn(new ArrayList<>());

        // Act
        //List<Contacto> contactosObtenidos = servicioContacto.traerContactosPorTipoYMetodo("Pediatra", "DOMAN");

        // Assert
        assertThrows(NoSeEncontraronProfesionalesEnLaBusqueda.class,
                ()->servicioContacto.traerProfesionalesPorTipoYMetodo("Pediatra", "DOMAN"));
        verify(repositorioMetodo, times(1)).buscarPorNombreDeMetodo("DOMAN");
        verify(repositorioTipoContacto, times(1)).buscarPorNombreDeTipo("Pediatra");
        verify(repositorioContacto, times(1)).traerProfesionalesPorTipoYMetodo("Pediatra", "DOMAN");
    }

}
