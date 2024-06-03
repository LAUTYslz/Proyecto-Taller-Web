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
public class RepositorioTipoContactoTest {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    RepositorioTipoContacto repositorioTipoContacto;

    @Test
    @Transactional
    @Rollback
    public void quePuedaGuardarUnTipoContacto(){
        givenNoExisteTipo();
        TipoContacto tipo = new TipoContacto();
        tipo.setNombre("Pediatra");
        TipoContacto guardado = whenGuardoTipoContacto(tipo);
        thenTipoContactoGuardado(guardado);
    }

    private void thenTipoContactoGuardado(TipoContacto guardado) {
        assertNotNull(guardado);
        assertThat(guardado.getNombre(), equalTo("Pediatra"));
        assertNotNull(guardado.getId());
    }

    private TipoContacto whenGuardoTipoContacto(TipoContacto tipo) {
        return repositorioTipoContacto.guardar(tipo);
    }

    private void givenNoExisteTipo() {
    }

    @Test
    @Transactional
    @Rollback
    public void quePuedaBuscarUnTipoContactoPorNombre(){
        TipoContacto tipoContacto = givenExisteTipo("Pediatra");
        TipoContacto tipoBuscado = whenBuscoTipoContactoPorNombre("Pediatra");
        thenObtengoTipoPorNombre(tipoBuscado);
    }

    private void thenObtengoTipoPorNombre(TipoContacto tipoBuscado) {
        assertThat(tipoBuscado.getNombre(), equalTo("Pediatra"));
    }

    private TipoContacto whenBuscoTipoContactoPorNombre(String nombrePediatra) {
        return repositorioTipoContacto.buscarPorNombre(nombrePediatra);
    }

    private TipoContacto givenExisteTipo(String pediatra) {
        TipoContacto tipo = new TipoContacto();
        tipo.setNombre(pediatra);
        return repositorioTipoContacto.guardar(tipo);
    }

    @Test
    @Transactional
    @Rollback
    public void quePuedaBuscartodosLosTiposDeContacto(){
        TipoContacto tipoContactoPediatra = givenExisteTipo("Pediatra");
        TipoContacto tipoContactoObstetra = givenExisteTipo("Obstetra");
        TipoContacto tipoContactoTienda = givenExisteTipo("Tienda");
        TipoContacto tipoContactoPsicopedagogo = givenExisteTipo("Psicopedagogo");
        List <TipoContacto> listaTipoBuscado = whenBuscoTodosTipoContacto();
        thenObtengoTiposDeContacto(listaTipoBuscado);
    }

    private void thenObtengoTiposDeContacto(List<TipoContacto> listaTipoBuscado) {
        assertThat(listaTipoBuscado.size(), equalTo(4));
    }

    private List<TipoContacto> whenBuscoTodosTipoContacto() {
        return repositorioTipoContacto.buscarTipos();
    }


}
