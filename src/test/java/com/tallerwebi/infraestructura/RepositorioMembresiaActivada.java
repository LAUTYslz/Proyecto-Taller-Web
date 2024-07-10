/*
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
public class RepositorioMembresiaActivada {

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
}
*/