package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Hijo;
import com.tallerwebi.dominio.ModeloTestRepositorio;
import com.tallerwebi.dominio.RepositorioVacunacion;
import com.tallerwebi.dominio.Vacunacion;
import com.tallerwebi.dominio.enums.Vacuna;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class VacunasRepositorioTest {

    @Autowired
    private RepositorioVacunacion repositorioVacunacion;
    @Autowired
    private RepositorioVacunacionImpl repositorioVacunacionImpl;
    @Autowired
    @InjectMocks
    private ServicioVacunacionImpl servicioVacunacionImpl;

    @Autowired
    private SessionFactory sessionFactory;

    private Hijo hijoMock;
    private Vacuna vacunaMock;
    private Date fechaAdministracionMock;

    @BeforeEach
    public void setUp() {
        hijoMock = new Hijo();
        vacunaMock = Vacuna.BCG;
        fechaAdministracionMock = new Date();
        repositorioVacunacion= mock(RepositorioVacunacion.class);
       servicioVacunacionImpl= mock(ServicioVacunacionImpl.class);
        repositorioVacunacionImpl= mock(RepositorioVacunacionImpl.class);
    }

    @Test
    public void testCrearUnaInstanciaDeVacunacion() {

        Hijo hijoMock = new Hijo();
        Vacuna vacunaMock = Vacuna.BCG;
        Date fechaAdministracionMock = new Date();

        Vacunacion vacunacion = new Vacunacion();
        vacunacion.setHijo(hijoMock);
        vacunacion.setVacuna(vacunaMock);
        vacunacion.setFechaAdministracion(fechaAdministracionMock);

        assertEquals(hijoMock, vacunacion.getHijo());
        assertEquals(vacunaMock, vacunacion.getVacuna());
        assertEquals(fechaAdministracionMock, vacunacion.getFechaAdministracion());
    }

    @Test
    public void testSetters() {
        Vacunacion vacunacion = new Vacunacion();

        Hijo hijoMock = new Hijo();
        vacunacion.setHijo(hijoMock);
        assertEquals(hijoMock, vacunacion.getHijo());

        Vacuna vacunaMock = Vacuna.BCG;
        vacunacion.setVacuna(vacunaMock);
        assertEquals(vacunaMock, vacunacion.getVacuna());

        Date fechaAdministracionMock = new Date();
        vacunacion.setFechaAdministracion(fechaAdministracionMock);
        assertEquals(fechaAdministracionMock, vacunacion.getFechaAdministracion());
    }

    @Test
    public void testGetters() {
        // Create a Vacunacion object with values
        Hijo hijoMock = new Hijo();
        Vacuna vacunaMock = Vacuna.BCG;
        Date fechaAdministracionMock = new Date();

        Vacunacion vacunacion = new Vacunacion();
        vacunacion.setHijo(hijoMock);
        vacunacion.setVacuna(vacunaMock);
        vacunacion.setFechaAdministracion(fechaAdministracionMock);

        // Test getter methods
        assertEquals(hijoMock, vacunacion.getHijo());
        assertEquals(vacunaMock, vacunacion.getVacuna());
        assertEquals(fechaAdministracionMock, vacunacion.getFechaAdministracion());
    }

    @Test
    public void testObtenerVacunasPendientes() {
        // Configuración del test
        Calendar fechaNacimientoCal = Calendar.getInstance();
        fechaNacimientoCal.set(2020, Calendar.JANUARY, 1); // Ejemplo de fecha de nacimiento del hijo

        Hijo hijo = new Hijo();
        hijo.setFecha_nacimiento(fechaNacimientoCal.getTime()); // Setear la fecha de nacimiento

        Vacuna vacuna = Vacuna.BCG; // Crear instancia de Vacuna
        Date fechaAdministracion = new Date(); // Crear instancia de Date

        Vacunacion vacunacion = new Vacunacion();
        vacunacion.setHijo(hijo);
        vacunacion.setVacuna(vacuna);
        vacunacion.setFechaAdministracion(fechaAdministracion);

        List<Vacunacion> vacunaciones = Arrays.asList(vacunacion);

        // Mock del comportamiento del repositorio
        when(repositorioVacunacion.obtenerVacunacionesPorHijo(anyLong())).thenReturn(vacunaciones);

        // Lógica del servicio a probar
        List<Vacuna> vacunasPendientes = servicioVacunacionImpl.obtenerVacunasPendientes(hijo.getFecha_nacimiento(), vacunaciones);

        // Verificación de resultados esperados
        assertNotNull(vacunasPendientes);
        assertEquals(18, vacunasPendientes.size()); // Verificar la cantidad esperada de vacunas pendientes
    }
    @Test
    public void testObtenerProximaVacuna() {
        // Configuración del test
        Calendar fechaNacimientoCal = Calendar.getInstance();
        fechaNacimientoCal.set(2024, Calendar.JULY, 1); // Ejemplo de fecha de nacimiento del hijo

        Hijo hijo = new Hijo();
        hijo.setFecha_nacimiento(fechaNacimientoCal.getTime()); // Setear la fecha de nacimiento

        Vacuna vacuna = Vacuna.PENTAVALENTE_PRIMERA; // Crear instancia de Vacuna
        Date fechaAdministracion = new Date(); // Crear instancia de Date

        Vacunacion vacunacion = new Vacunacion();
        vacunacion.setHijo(hijo);
        vacunacion.setVacuna(vacuna);
        vacunacion.setFechaAdministracion(fechaAdministracion);

        List<Vacunacion> vacunaciones = Arrays.asList(vacunacion);

        // Mock del comportamiento del repositorio
        when(repositorioVacunacion.obtenerVacunacionesPorHijo(anyLong())).thenReturn(vacunaciones);

        // Lógica del servicio a probar
        List<Vacuna> proximaVacuna = servicioVacunacionImpl.obtenerProximaVacuna(hijo.getFecha_nacimiento(), vacunaciones);

        // Verificación de resultados esperados
        assertNotNull(proximaVacuna);
        assertEquals(Vacuna.PENTAVALENTE_PRIMERA, proximaVacuna.get(1));
    }
    }




