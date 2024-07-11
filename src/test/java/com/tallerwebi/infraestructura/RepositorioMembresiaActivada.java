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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioMembresiaActivada {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private RepositorioProfesional repositorioProfesional;
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private RepositorioMetodo repositorioMetodo;
    @Autowired
    private RepositorioTipoProfesional repositorioTipoProfesional;
    @Autowired
    private RepositorioMembresiaActivadaImpl repositorioMembresiaActivada;

    @Test
    @Transactional
    @Rollback


    public void quesepuedaGuardarConsulta() {
        // Simular existencia de entidades
        Usuario usuario = givenExisteUsuario("Ayelen");
        TipoProfesional tipoPediatra = givenExisteTipo("Pediatria");
        Metodo metodo = givenExisteMetodo("Metodo");
        Profesional contacto = givenExisteProfesional("Dr. Pérez", tipoPediatra, metodo);

        // Crear consulta
        Consulta consulta = new Consulta();
        consulta.setUsuario(usuario);
        consulta.setProfesional(contacto); // Asignar el profesional a la consulta, si es necesario

        // Guardar consulta
        repositorioMembresiaActivada.guardarConsulta(consulta);

        // Verificar que la consulta se haya guardado correctamente
        assertNotNull(consulta.getId()); // Verificar que se haya generado un ID


    }

    // Métodos para simular existencia de entidades

    private Usuario givenExisteUsuario(String ayelen) {
        Usuario usuario = new Usuario();

       repositorioUsuario.guardar(usuario);
        return usuario;
    }

    private Hijo givenExisteHijo(Usuario usuario) {
        Hijo hijo = new Hijo();
        hijo.setId(1L); // Supongamos que el hijo tiene ID 1
        hijo.setUsuario(usuario); // Asignar usuario al hijo
        repositorioUsuario.guardarHijo(hijo);
        return hijo;
    }

    private TipoProfesional givenExisteTipo(String nombre) {
        TipoProfesional tipoProfesional = new TipoProfesional();
        tipoProfesional.setNombre(nombre);
        // Lógica para guardar el tipo de profesional si es necesario

        return tipoProfesional;
    }

    private Metodo givenExisteMetodo(String nombre) {
        Metodo metodo = new Metodo();
        metodo.setNombre(nombre);
        repositorioMetodo.guardar(metodo);
        // Lógica para guardar el método si es necesario
        return metodo;
    }

    private Profesional givenExisteProfesional(String nombre, TipoProfesional tipoProfesional, Metodo metodo) {
        Profesional profesional = new Profesional();
        profesional.setNombre(nombre);
        profesional.setTipo(tipoProfesional);
        profesional.setMetodo(metodo);
        repositorioProfesional.guardar(profesional);
        // Lógica para guardar el profesional si es necesario
        return profesional;
    }
}

