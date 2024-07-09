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

public class RepositorioMetodoTest {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    RepositorioMetodo repositorioMetodo;

    @Test
    @Transactional
    @Rollback
    public void quePuedaGuardarUnMetodo(){
        givenNoExisteMetodo();
        Metodo metodo = new Metodo();
        metodo.setNombre("DOMAN");
        Metodo guardado = whenGuardoMetodo(metodo);
        thenMetodoGuardado(guardado);
    }

    private void thenMetodoGuardado(Metodo guardado) {
        assertNotNull(guardado);
        assertThat(guardado.getNombre(), equalTo("DOMAN"));
        assertNotNull(guardado.getId());
    }

    private Metodo whenGuardoMetodo(Metodo metodo) {
        return repositorioMetodo.guardar(metodo);
    }

    private void givenNoExisteMetodo() {
    }

    @Test
    @Transactional
    @Rollback
    public void quePuedaBuscarUnMetodo(){
        //Metodo metodo = givenExisteMetodo("Doman");
        Metodo metodo = new Metodo();
        metodo.setNombre("DOMAN");
        repositorioMetodo.guardar(metodo);
        Metodo metodoBuscado = whenBuscoMetodoPorNombre("Doman");
        thenEncuentroMetodo(metodoBuscado);
    }

    private void thenEncuentroMetodo(Metodo metodoBuscado) {
        assertNotNull(metodoBuscado);
        assertThat(metodoBuscado.getNombre(), equalTo("DOMAN"));
    }

    private Metodo whenBuscoMetodoPorNombre(String nombreMetodo) {
        return repositorioMetodo.buscarPorNombreDeMetodo(nombreMetodo);
    }

    private Metodo givenExisteMetodo(String nombreMetodo) {
        Metodo metodo = new Metodo();
        metodo.setNombre(nombreMetodo);
        return repositorioMetodo.guardar(metodo);
    }

    @Test
    @Transactional
    @Rollback
    public void quePuedaBuscarTodosLosMetodos(){
        Metodo metodoDoman = givenExisteMetodo("Doman");
        Metodo metodoWaldorf = givenExisteMetodo("Waldorf");
        Metodo metodoMontessori = givenExisteMetodo("Montessori");
        List<Metodo> listaMetodos = whenBuscoMetodos();
        thenEncuentroMetodos(listaMetodos);
    }

    private void thenEncuentroMetodos(List<Metodo> listaMetodos) {
        assertThat(listaMetodos.size(),equalTo(3));
    }

    private List<Metodo> whenBuscoMetodos() {
        return repositorioMetodo.buscarMetodos();
    }

    @Test
    @Transactional
    @Rollback
    public void quePuedaBuscarUnMetodoPorId(){
        //Metodo metodo = givenExisteMetodo("Doman");
        Metodo metodo = new Metodo();
        metodo.setNombre("DOMAN");
        repositorioMetodo.guardar(metodo);
        Long idMetodo = metodo.getId();
        Metodo metodoBuscado = whenBuscoMetodoPorId(idMetodo);
        thenEncuentroMetodoPorId(metodoBuscado);
    }

    private void thenEncuentroMetodoPorId(Metodo metodoBuscado) {
        assertNotNull(metodoBuscado);
        assertThat(metodoBuscado.getNombre(), equalTo("DOMAN"));
        assertThat(metodoBuscado.getId(), equalTo(1L));
    }

    private Metodo whenBuscoMetodoPorId(Long idMetodo) {
        return repositorioMetodo.traerMetodoPorId(idMetodo);
    }
}


