package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service("servicioMembresiaActivada")
@Transactional
public class ServicioMembresiaActivadaImpl implements ServicioMembresiaActivada {
    private final RepositorioMembresia repositorioMembresia;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioAdmi repositorioAdmi;
    private final RepositorioMembresiaActivada repositorioMembresiaActivada;
    private final ServicioLogin servicioLogin;

    public ServicioMembresiaActivadaImpl(RepositorioMembresia repositorioMembresia, RepositorioUsuario repositorioUsuario, RepositorioAdmi repositorioAdmi, RepositorioMembresiaActivada repositorioMembresiaActivada, ServicioLogin servicioLogin) {
        this.repositorioMembresia = repositorioMembresia;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioAdmi = repositorioAdmi;
        this.repositorioMembresiaActivada = repositorioMembresiaActivada;
        this.servicioLogin = servicioLogin;
    }


    @Override
    public Consulta realizarConsulta(Consulta consulta, Hijo hijo, Profesional profesional, Usuario usuario) {
        repositorioMembresiaActivada.guardarConsulta(consulta);
        consulta.setUsuario(usuario);

       consulta.setProfesional(profesional);


     consulta.setHijo(hijo);

        agregarFecha(consulta);
        agregarEstado(consulta);
        repositorioMembresiaActivada.actualizarConsulta(consulta);


        return consulta;
    }

    @Override
    public Consulta buscarConsultaPorUsuario(Long usuarioid) {
        return (Consulta) repositorioMembresiaActivada.buscarConsulta(usuarioid);
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
