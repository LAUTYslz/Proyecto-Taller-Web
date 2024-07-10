package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControladorMembresiaActivadaTest {
    private ServicioMembresiaActivada servicioMembresiaActivada;
    private ServicioLogin servicioLogin;
    private ServicioAdmi servicioAdmi;
    private ServicioProfesional servicioProfesional;
    private ServicioMetodo servicioMetodo;
    private ServicioTipoProfesional servicioTipoProfesional;

    private MockMvc mockMvc;
    @InjectMocks
    private ControladorMembresiaActivada controladorMembresiaActivada;

    @BeforeEach
    public void setUp() {
        servicioProfesional = mock(ServicioProfesional.class);
        servicioLogin = mock(ServicioLogin.class);
        servicioAdmi = mock(ServicioAdmi.class);
        servicioMembresiaActivada = mock(ServicioMembresiaActivada.class);
        servicioMetodo = mock(ServicioMetodo.class);
        servicioTipoProfesional = mock(ServicioTipoProfesional.class);
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controladorMembresiaActivada).build();


    }
    @Test
    public void testCrearConsulta() {
        Consulta consultaEsperada = new Consulta();
        consultaEsperada.setRespuesta("hola");
        consultaEsperada.setId(1L);
// cuando traigo al mockito y le digo que esa metodo me tine que retornar lo que le pongo a then return
        when(servicioMembresiaActivada.obtenerConsultaPorId(1L)).thenReturn(consultaEsperada);
// aca lo que obtengo
        Consulta consultaObtendida = servicioMembresiaActivada.obtenerConsultaPorId(1L);
        // verifico que se llamo una vez al metodo
        verify(servicioMembresiaActivada, times(1)).obtenerConsultaPorId(1L);
        //verifico que lo que obtbe sea igual a la que cree
        assertThat(consultaObtendida.getId(), equalTo(consultaEsperada.getId()));
    }
    @Test
    public void testBuscarConsultaCreadaPorUsuario() {
        Consulta consultaEsperada = new Consulta();
        consultaEsperada.setRespuesta("hola");
        consultaEsperada.setId(1L);


        Usuario usuarioEsperada = new Usuario();
        usuarioEsperada.setNombre("hola");
        usuarioEsperada.setId(1L);
        Long usuarioid = usuarioEsperada.getId();
        consultaEsperada.setUsuario(usuarioEsperada);
        List<Consulta> consultas = new ArrayList<Consulta>();
        consultas.add(consultaEsperada);

// cuando traigo al mockito y le digo que esa metodo me tine que retornar lo que le pongo a then return
        when(servicioMembresiaActivada.buscarConsultaPorUsuario (usuarioid)).thenReturn(consultas);
        List<Consulta> listaObtenida = whenBuscoreConsultasPorUsuario(usuarioid);


        // verifico que se llamo una vez al metodo
        verify(servicioMembresiaActivada, times(1)).buscarConsultaPorUsuario(1L);
        //verifico que lo que obtbe sea igual a la que cree
        assertThat(listaObtenida.size(), equalTo(1));
    }
    private List<Consulta> whenBuscoreConsultasPorUsuario(Long id) {
        return servicioMembresiaActivada.buscarConsultaPorUsuario(id);
    }
    @Test
    public void testObtenerVariasConsultasCreadas() {
        Consulta consulta = new Consulta();
        Consulta consulta1 = new Consulta();
        Consulta consulta2 = new Consulta();


        List<Consulta> consultas = new ArrayList<Consulta>();
        consultas.add(consulta);
       consultas.add(consulta1);
       consultas.add(consulta2);

        when(servicioMembresiaActivada.listaDeConsultascreadas()).thenReturn(consultas);

        List<Consulta> listaObtenida = whenBuscoConsultas();

        verify(servicioMembresiaActivada, times(1)).listaDeConsultascreadas();
        assertThat(listaObtenida.size(), equalTo(3));
    }
    private List<Consulta> whenBuscoConsultas() {
        return servicioMembresiaActivada.listaDeConsultascreadas();
    }

    @Test
    public void testMostrarMembresiaActivada() throws UsuarioInexistente, UsuarioExistente, UsuarioInexistente, UsuarioExistente {
        // Mock HttpServletRequest
        MockHttpServletRequest request = new MockHttpServletRequest();

        // Mock datos de usuario y membresía
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setMembresia(new DatosMembresia());

        List<Hijo> hijos = new ArrayList<>();
        hijos.add(new Hijo());

        Consulta consulta = new Consulta();
        List<Consulta> consultas = new ArrayList<>();
        consultas.add(consulta);

        // Configurar comportamiento del servicioLogin
        when(servicioLogin.obtenerUsuarioActual(request)).thenReturn(usuario);
        when(servicioLogin.buscarHijosPorId(1L)).thenReturn(hijos);

        // Configurar comportamiento del servicioMembresiaActivada
        when(servicioMembresiaActivada.buscarConsultaPorUsuario(1L)).thenReturn(consultas);

        // Llamar al método del controlador
        ModelAndView modelAndView = controladorMembresiaActivada.mostrarMembresiaActivada(request);

        // Verificar el resultado esperado
        assertEquals("usuarioMembresia", modelAndView.getViewName());
        assertEquals(usuario, modelAndView.getModel().get("usuario"));
        assertEquals(usuario.getMembresia(), modelAndView.getModel().get("membresia"));
        assertEquals(hijos, modelAndView.getModel().get("hijos"));
        assertEquals(consultas, modelAndView.getModel().get("consultas"));

        // Verificar que los métodos del servicio fueron invocados correctamente
        verify(servicioLogin, times(1)).obtenerUsuarioActual(request);
        verify(servicioLogin, times(1)).buscarHijosPorId(1L);
        verify(servicioMembresiaActivada, times(1)).buscarConsultaPorUsuario(1L);
    }
    @Test
    public void testVerMetodos() throws UsuarioInexistente, UsuarioExistente {
        // Mock HttpServletRequest
        MockHttpServletRequest request = new MockHttpServletRequest();

        // Mock datos de usuario y membresía
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setMembresia(new DatosMembresia());

        List<Hijo> hijos = new ArrayList<>();
        hijos.add(new Hijo());
        Metodo metodo= new Metodo();
        List<Metodo> metodos = new ArrayList<>();
        metodos.add(metodo);

        // Configurar comportamiento del servicioLogin
        when(servicioLogin.obtenerUsuarioActual(request)).thenReturn(usuario);
        when(servicioLogin.buscarHijosPorId(1L)).thenReturn(hijos);

        // Configurar comportamiento del servicioMetodo
        when(servicioMetodo.buscarMetodos()).thenReturn(metodos);

        // Llamar al método del controlador
        ModelAndView modelAndView = controladorMembresiaActivada.verMetodos(request);

        // Verificar el resultado esperado
        assertEquals("info", modelAndView.getViewName());

        assertEquals(usuario, modelAndView.getModel().get("usuario"));
        assertEquals(usuario.getMembresia(), modelAndView.getModel().get("membresia"));
        assertEquals(hijos, modelAndView.getModel().get("hijos"));
        assertEquals(metodos, modelAndView.getModel().get("metodos"));

        // Verificar que los métodos del servicio fueron invocados correctamente
        verify(servicioLogin, times(1)).obtenerUsuarioActual(request);
        verify(servicioLogin, times(1)).buscarHijosPorId(1L);
        verify(servicioMetodo, times(1)).buscarMetodos();
    }

}


