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
    public Consulta realizarConsulta(Consulta consulta, Long hijo, Long profesional, Usuario usuario) {

        consulta.setUsuario(usuario);
      Profesional profe=  servicioProfesional.obtenerPorId(profesional);
       consulta.setProfesional(profe);
        Hijo hijonuevo = servicioLogin.busquedahijo(hijo);
        consulta.setHijo(hijonuevo);

        agregarFecha(consulta);
        agregarEstado(consulta);
        repositorioMembresiaActivada.guardarConsulta(consulta);


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
