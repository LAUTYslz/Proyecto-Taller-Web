package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.CantidadDeConsultasAgotadas;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("servicioMembresiaActivada")
@Transactional
public class ServicioMembresiaActivadaImpl implements ServicioMembresiaActivada {
    private final RepositorioMembresia repositorioMembresia;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioAdmi repositorioAdmi;
    private final RepositorioMembresiaActivada repositorioMembresiaActivada;
    private final ServicioLogin servicioLogin;
    private final ServicioProfesional servicioProfesional;

    public ServicioMembresiaActivadaImpl(RepositorioMembresia repositorioMembresia, RepositorioUsuario repositorioUsuario, RepositorioAdmi repositorioAdmi, RepositorioMembresiaActivada repositorioMembresiaActivada, ServicioLogin servicioLogin, ServicioProfesional servicioProfesional) {
        this.repositorioMembresia = repositorioMembresia;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioAdmi = repositorioAdmi;
        this.repositorioMembresiaActivada = repositorioMembresiaActivada;
        this.servicioLogin = servicioLogin;
        this.servicioProfesional = servicioProfesional;
    }

    @Override
    public Consulta realizarConsulta(Consulta consulta, Long hijoId, Long profesionalId, Usuario usuario) throws CantidadDeConsultasAgotadas {
        // Obtener fecha actual
        LocalDate fechaActual = LocalDate.now();
        int mesActual = fechaActual.getMonthValue();
        int anioActual = fechaActual.getYear();

        // Buscar la cantidad de consultas existentes para este usuario en este mes
        int cantidadConsultas = contarConsultasPorUsuarioMesAnio(usuario, mesActual);

        if (cantidadConsultas < 3) {
            // Aún hay espacio para más consultas, crear una nueva instancia de consulta
            consulta.setUsuario(usuario);
            consulta.setMensaje(consulta.getMensaje());
            consulta.setProfesional(servicioProfesional.obtenerPorId(profesionalId));
            consulta.setHijo(servicioLogin.busquedahijo(hijoId));
            consulta.setCantidad(cantidadConsultas + 1); // Nueva consulta, incrementar cantidad
            consulta.setMes(mesActual);
            consulta.setAnio(anioActual);
            agregarEstado(consulta);
            agregarFecha(consulta);
            repositorioMembresiaActivada.guardarConsulta(consulta);
        } else {
            // Se ha alcanzado el límite de consultas para este mes y año
            throw new CantidadDeConsultasAgotadas();
        }

        return consulta; // Devolver la nueva consulta creada
    }


    private Consulta buscarConsultaActualPorUsuarioProfesionalMesAnio(Usuario usuario, int mesActual, int anioActual) {
        // Buscar todas las consultas del usuario en el mes y año dados
        List<Consulta> consultas = buscarConsultaPorUsuario(usuario.getId());

        // Filtrar las consultas por mes y año
        List<Consulta> consultasFiltradas = consultas.stream()
                .filter(consulta -> consulta.getMes() != null && consulta.getAnio() != null &&
                        consulta.getMes().equals(mesActual) && consulta.getAnio().equals(anioActual))
                .collect(Collectors.toList());

        // Si hay consultas filtradas, devolver la primera (la actual)
        if (!consultasFiltradas.isEmpty()) {
            return consultasFiltradas.get(0);
        } else {
            return null;
        }
    }

    // busco una lista de consultas, de esa lista, cuanto cuantas coinciden con el mismo mes.
    private int contarConsultasPorUsuarioMesAnio(Usuario usuario,  int mesActual) {
        // Obtener la lista de consultas del usuario
        List<Consulta> consultas = buscarConsultaPorUsuario(usuario.getId());

        // Contar las consultas que coinciden con el mes
        long cantidad = consultas.stream() // utilizo stream
                .filter(consulta -> consulta.getMes() != null &&
                        consulta.getMes() == mesActual )
                .count();

        return (int) cantidad;
    }


    @Override
    public List<Consulta> buscarConsultaPorUsuario(Long usuarioid) {
        return repositorioMembresiaActivada.buscarConsulta(usuarioid);
    }

    @Override
    public List<Consulta> buscarConsultasPorProfesionales(String email) {
        return repositorioMembresiaActivada.buscarConsultasPorProfesionales(email);
    }

    @Override
    public Consulta obtenerConsultaPorId(Long consultaId) {
        return repositorioMembresiaActivada.buscarConsultaPorId(consultaId);
    }

    @Override
    public void respuestaDeProfesionalAConsulta(Long consultaId, String respuesta) {
        Consulta buscarConsulta = obtenerConsultaPorId(consultaId);
        buscarConsulta.setRespuesta(respuesta);
        buscarConsulta.setEstado(Mensaje.RESPONDIDO);
        repositorioMembresiaActivada.actualizarConsulta(buscarConsulta);
    }

    private void actualizarConsulta(Consulta consulta) {
          repositorioMembresiaActivada.actualizarConsulta(consulta);
    }

    private void agregarEstado(Consulta consulta) {
        consulta.setEstado(Mensaje.SIN_LEER);


    }

    private void agregarFecha(Consulta consulta) {
        Date fechaActual = new Date(); // Obtiene la fecha y hora actual del sistema

        consulta.setFecha(fechaActual); // Asigna la fecha actual a la consulta
    }
}
