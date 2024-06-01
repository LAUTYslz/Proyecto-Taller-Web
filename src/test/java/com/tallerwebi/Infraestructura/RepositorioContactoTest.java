package com.tallerwebi.Infraestructura;


import com.tallerwebi.dominio.*;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioContactoTest {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private RepositorioContacto repositorioContacto;
    @Autowired
    private RepositorioMetodo repositorioMetodo;
    @Autowired
    private RepositorioTipoContactoImpl repositorioTipoContacto;

    @Test
    @Transactional
    @Rollback
    public void quePuedaGuardarContactos() {
        TipoContacto tipoPediatra = givenExisteTipo("Pediatra");
        Metodo metodoWaldorf = givenExisteMetodo("Waldorf");

        Contacto contacto = givenExisteContacto("Dr. Pérez", tipoPediatra, metodoWaldorf);

        assertNotNull(contacto.getId());
        assertThat("Dr. Pérez", equalTo(contacto.getNombre()));
        assertThat(tipoPediatra, equalTo(contacto.getTipo()));
        assertThat(metodoWaldorf, equalTo(contacto.getMetodo()));

    }

    private Metodo givenExisteMetodo(String nombreMetodo) {
        Metodo metodo = new Metodo();
        metodo.setNombre(nombreMetodo);
        repositorioMetodo.guardar(metodo);
        return metodo;
    }

    private TipoContacto givenExisteTipo(String nombreTipo) {
        TipoContacto tipo = new TipoContacto();
        tipo.setNombre(nombreTipo);
        repositorioTipoContacto.guardar(tipo);
        return tipo;
    }

    private Contacto givenExisteContacto(String nombre, TipoContacto tipoPediatra, Metodo metodoWaldorf) {
        Contacto contacto = new Contacto();
        contacto.setNombre(nombre);
        contacto.setTipo(tipoPediatra);
        contacto.setMetodo(metodoWaldorf);
        sessionFactory.getCurrentSession().save(contacto);
        return contacto;
    }

    @Test
    @Transactional
    @Rollback
    public void quePuedaObtenerTodosLosContactos() {
        TipoContacto tipoPediatra = givenExisteTipo("Pediatra");
        Metodo metodoWaldorf = givenExisteMetodo("Waldorf");

        Contacto contacto = givenExisteContacto("Dr. Pérez", tipoPediatra, metodoWaldorf);
        Contacto contacto1 = givenExisteContacto("Dr. Sanchez", tipoPediatra, metodoWaldorf);
        Contacto contacto2 = givenExisteContacto("Dr. Salazar", tipoPediatra, metodoWaldorf);

        List<Contacto> listaContactos =  whenObtengoContactos();
        thenObtengoUsuarios(listaContactos);
    }

    private void thenObtengoUsuarios(List<Contacto> listaContactos) {
        assertThat(listaContactos.size(), equalTo(3));
    }

    private List<Contacto> whenObtengoContactos() {
        return repositorioContacto.traerContactos();
    }

    @Test
    @Transactional
    @Rollback
    public void QuePuedaObtenerTodosLosClientesPorMetodo() {
        TipoContacto tipoPediatra = givenExisteTipo("Pediatra");
        Metodo metodoWaldorf = givenExisteMetodo("Waldorf");
        Metodo metodoDoman = givenExisteMetodo("Doman");

        Contacto contacto = givenExisteContacto("Dr. Pérez", tipoPediatra, metodoWaldorf);
        Contacto contacto1 = givenExisteContacto("Dr. Sanchez", tipoPediatra, metodoWaldorf);
        Contacto contacto2 = givenExisteContacto("Dr. Salazar", tipoPediatra, metodoDoman);

        List<Contacto> listaContactos =  whenObtengoContactosPorMetodo("Waldorf");
        thenObtengoUsuariosPorMetodo(listaContactos);
    }

    private void thenObtengoUsuariosPorMetodo(List<Contacto> listaContactos) {
        assertThat(listaContactos.size(), equalTo(2));
    }

    private List<Contacto> whenObtengoContactosPorMetodo(String nombreMetodo) {
        return repositorioContacto.traerContactosPorMetodo(nombreMetodo);
    }

    @Test
    @Transactional
    @Rollback
    public void QuePuedaObtenerTodosLosClientesPorTipoDeContacto() {
        TipoContacto tipoPediatra = givenExisteTipo("Pediatra");
        TipoContacto tipoObstetra = givenExisteTipo("Obstetra");
        Metodo metodoDoman = givenExisteMetodo("Doman");

        Contacto contacto = givenExisteContacto("Dr. Pérez", tipoPediatra, metodoDoman);
        Contacto contacto1 = givenExisteContacto("Dr. Sanchez", tipoObstetra, metodoDoman);
        Contacto contacto2 = givenExisteContacto("Dr. Salazar", tipoObstetra, metodoDoman);

        List<Contacto> listaContactos =  whenObtengoContactosPorTipo("Obstetra");
        thenObtengoUsuariosPorTipo(listaContactos);
    }

    private void thenObtengoUsuariosPorTipo(List<Contacto> listaContactos) {
        assertThat(listaContactos.size(), equalTo(2));
    }

    private List<Contacto> whenObtengoContactosPorTipo(String nombreTipo) {
        return repositorioContacto.traerContactosPorTipo(nombreTipo);
    }

}
