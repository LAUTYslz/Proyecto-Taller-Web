package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.ServicioMembresiaActivadaImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ControladorConsultaTest {

    private ServicioProfesional servicioProfesional;
    private ServicioLogin servicioLogin;
    private ServicioMembresiaActivada servicioMembresiaActivada;
    private ControladorProfesional controladorProfesional;


    @BeforeEach
    public void setUp() {
        servicioProfesional = mock(ServicioProfesional.class);
        controladorProfesional = new ControladorProfesional(servicioProfesional, servicioLogin, servicioMembresiaActivada);
    }

    @Test
    public void testObtenerProfesionales() {

        Profesional contacto1 = new Profesional();
        contacto1.setNombre("Dr. Salazar");
        Profesional contacto2 = new Profesional();
        contacto2.setNombre("Dra. López");

        List<Profesional> contactos = new ArrayList<Profesional>();
        contactos.add(contacto1);
        contactos.add(contacto2);

        when(servicioProfesional.traerProfesionales()).thenReturn(contactos);

        List<Profesional> listaObtenida = whenBuscoContactos();

        verify(servicioProfesional, times(1)).traerProfesionales();
        assertThat(listaObtenida.size(), equalTo(2));
    }
}