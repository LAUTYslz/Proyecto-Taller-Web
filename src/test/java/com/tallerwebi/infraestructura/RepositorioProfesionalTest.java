package com.tallerwebi.infraestructura;


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
public class RepositorioProfesionalTest {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private RepositorioProfesional repositorioProfesional;
    @Autowired
    private RepositorioMetodo repositorioMetodo;
    @Autowired
    private RepositorioTipoProfesional repositorioTipoProfesional;

    @Test
    @Transactional
    @Rollback
    public void quePuedaGuardarContactos() {
        TipoProfesional tipoPediatra = givenExisteTipo("Pediatra");
        Metodo metodoWaldorf = givenExisteMetodo("Waldorf");

        Profesional contacto = givenExisteContacto("Dr. Pérez", tipoPediatra, metodoWaldorf);

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

    private TipoProfesional givenExisteTipo(String nombreTipo) {
        TipoProfesional tipo = new TipoProfesional();
        tipo.setNombre(nombreTipo);
        repositorioTipoProfesional.guardar(tipo);
        return tipo;
    }

    private Profesional givenExisteContacto(String nombre, TipoProfesional tipoPediatra, Metodo metodoWaldorf) {
        Profesional contacto = new Profesional();
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
        TipoProfesional tipoPediatra = givenExisteTipo("Pediatra");
        Metodo metodoWaldorf = givenExisteMetodo("Waldorf");

        Profesional contacto = givenExisteContacto("Dr. Pérez", tipoPediatra, metodoWaldorf);
        Profesional contacto1 = givenExisteContacto("Dr. Sanchez", tipoPediatra, metodoWaldorf);
        Profesional contacto2 = givenExisteContacto("Dr. Salazar", tipoPediatra, metodoWaldorf);

        List<Profesional> listaContactos =  whenObtengoContactos();
        thenObtengoUsuarios(listaContactos);
    }

    private void thenObtengoUsuarios(List<Profesional> listaContactos) {
        assertThat(listaContactos.size(), equalTo(3));
    }

    private List<Profesional> whenObtengoContactos() {
        return repositorioProfesional.traerProfesionales();
    }

    @Test
    @Transactional
    @Rollback
    public void QuePuedaObtenerTodosLosClientesPorMetodo() {
        TipoProfesional tipoPediatra = givenExisteTipo("Pediatra");
        Metodo metodoWaldorf = givenExisteMetodo("Waldorf");
        Metodo metodoDoman = givenExisteMetodo("Doman");

        Profesional contacto = givenExisteContacto("Dr. Pérez", tipoPediatra, metodoWaldorf);
        Profesional contacto1 = givenExisteContacto("Dr. Sanchez", tipoPediatra, metodoWaldorf);
        Profesional contacto2 = givenExisteContacto("Dr. Salazar", tipoPediatra, metodoDoman);

        List<Profesional> listaContactos =  whenObtengoContactosPorMetodo("Waldorf");
        thenObtengoUsuariosPorMetodo(listaContactos);
    }

    private void thenObtengoUsuariosPorMetodo(List<Profesional> listaContactos) {
        assertThat(listaContactos.size(), equalTo(2));
    }

    private List<Profesional> whenObtengoContactosPorMetodo(String nombreMetodo) {
        return repositorioProfesional.traerProfesionalesPorMetodo(nombreMetodo);
    }

    @Test
    @Transactional
    @Rollback
    public void QuePuedaObtenerTodosLosClientesPorTipoDeContacto() {
        TipoProfesional tipoPediatra = givenExisteTipo("Pediatra");
        TipoProfesional tipoObstetra = givenExisteTipo("Obstetra");
        Metodo metodoDoman = givenExisteMetodo("Doman");

        Profesional contacto = givenExisteContacto("Dr. Pérez", tipoPediatra, metodoDoman);
        Profesional contacto1 = givenExisteContacto("Dr. Sanchez", tipoObstetra, metodoDoman);
        Profesional contacto2 = givenExisteContacto("Dr. Salazar", tipoObstetra, metodoDoman);

        List<Profesional> listaContactos =  whenObtengoContactosPorTipo("Obstetra");
        thenObtengoUsuariosPorTipo(listaContactos);
    }

    private void thenObtengoUsuariosPorTipo(List<Profesional> listaContactos) {
        assertThat(listaContactos.size(), equalTo(2));
    }

    private List<Profesional> whenObtengoContactosPorTipo(String nombreTipo) {
        return repositorioProfesional.traerProfesionalesPorTipo(nombreTipo);
    }

}
