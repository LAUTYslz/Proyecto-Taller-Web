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
public class RepositorioTipoProfesionalTest {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    RepositorioTipoProfesional repositorioTipoContacto;

    @Test
    @Transactional
    @Rollback
    public void quePuedaGuardarUnTipoContacto(){
        givenNoExisteTipo();
        TipoProfesional tipo = new TipoProfesional();
        tipo.setNombre("Pediatra");
        TipoProfesional guardado = whenGuardoTipoContacto(tipo);
        thenTipoContactoGuardado(guardado);
    }

    private void thenTipoContactoGuardado(TipoProfesional guardado) {
        assertNotNull(guardado);
        assertThat(guardado.getNombre(), equalTo("Pediatra"));
        assertNotNull(guardado.getId());
    }

    private TipoProfesional whenGuardoTipoContacto(TipoProfesional tipo) {
        return repositorioTipoContacto.guardar(tipo);
    }

    private void givenNoExisteTipo() {
    }

    @Test
    @Transactional
    @Rollback
    public void quePuedaBuscarUnTipoContactoPorNombre(){
        TipoProfesional tipoContacto = givenExisteTipo("Pediatra");
        TipoProfesional tipoBuscado = whenBuscoTipoContactoPorNombre("Pediatra");
        thenObtengoTipoPorNombre(tipoBuscado);
    }

    private void thenObtengoTipoPorNombre(TipoProfesional tipoBuscado) {
        assertThat(tipoBuscado.getNombre(), equalTo("Pediatra"));
    }

    private TipoProfesional whenBuscoTipoContactoPorNombre(String nombrePediatra) {
        return repositorioTipoContacto.buscarPorNombreDeTipo(nombrePediatra);
    }

    private TipoProfesional givenExisteTipo(String pediatra) {
        TipoProfesional tipo = new TipoProfesional();
        tipo.setNombre(pediatra);
        return repositorioTipoContacto.guardar(tipo);
    }

    @Test
    @Transactional
    @Rollback
    public void quePuedaBuscartodosLosTiposDeContacto(){
        TipoProfesional tipoContactoPediatra = givenExisteTipo("Pediatra");
        TipoProfesional tipoContactoObstetra = givenExisteTipo("Obstetra");
        TipoProfesional tipoContactoTienda = givenExisteTipo("Tienda");
        TipoProfesional tipoContactoPsicopedagogo = givenExisteTipo("Psicopedagogo");
        List <TipoProfesional> listaTipoBuscado = whenBuscoTodosTipoContacto();
        thenObtengoTiposDeContacto(listaTipoBuscado);
    }

    private void thenObtengoTiposDeContacto(List<TipoProfesional> listaTipoBuscado) {
        assertThat(listaTipoBuscado.size(), equalTo(4));
    }

    private List<TipoProfesional> whenBuscoTodosTipoContacto() {
        return repositorioTipoContacto.buscarTipos();
    }


}
