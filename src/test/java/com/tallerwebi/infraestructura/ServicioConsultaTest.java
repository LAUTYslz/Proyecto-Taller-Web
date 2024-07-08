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


public class ServicioConsultaTest {
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
}