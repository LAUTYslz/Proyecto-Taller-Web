package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ServicioTurnoImpl implements ServicioTurno {
    private final RepositorioTurno repositorioTurno;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioProfesional repositorioProfesional;

    @Autowired
    public ServicioTurnoImpl(RepositorioTurno repositorioTurno, RepositorioUsuario repositorioUsuario, RepositorioProfesional repositorioProfesional) {
        this.repositorioTurno = repositorioTurno;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioProfesional = repositorioProfesional;
    }

    public Turno agendarTurno(Long usuarioId, Long profesionalId, Date fechaHora) {
        Usuario usuario = repositorioUsuario.buscarPorId(usuarioId);
                //.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Profesional profesional = repositorioProfesional.buscarProfesionalPorId(profesionalId);
                //.orElseThrow(() -> new RuntimeException("Profesional no encontrado"));
        if (profesional == null) {
            throw new RuntimeException("Profesional no encontrado");
        }

        Turno turno = new Turno();
        turno.setUsuario(usuario);
        turno.setProfesional(profesional);
        turno.setFechaHora(fechaHora);
        turno.setEstado(EstadoTurno.PENDIENTE);

        return repositorioTurno.guardarTurno(turno);
    }

    public void actualizarEstadoTurno(Long turnoId, EstadoTurno nuevoEstado) {
        Turno turno = repositorioTurno.buscarPorId(turnoId);
                //.orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        if (turno == null) {
            throw new RuntimeException("Turno no encontrado");
        }

        turno.setEstado(nuevoEstado);
        repositorioTurno.actualizarTurno(turno);
    }

    public List<Turno> obtenerTurnosPorUsuario(Long usuarioId) {
        Usuario usuario = repositorioUsuario.buscarPorId(usuarioId);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return repositorioTurno.buscarTurnosPorUsuario(usuario);
    }

    public List<Turno> obtenerTurnosPorProfesional(Long profesionalId) {
        Profesional profesional = repositorioProfesional.buscarProfesionalPorId(profesionalId);
        if (profesional == null) {
            throw new RuntimeException("Profesional no encontrado");
        }
        return repositorioTurno.buscarTurnosPorProfesional(profesional);
    }

    @Override
    public void eliminarTurno(Long turnoId) {
        Turno turno = repositorioTurno.buscarPorId(turnoId);
        repositorioTurno.eliminar(turno);
    }
}
