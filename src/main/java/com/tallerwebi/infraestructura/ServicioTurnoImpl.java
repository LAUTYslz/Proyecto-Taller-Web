package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.ElUsuarioYaTieneTurnoEnEsaFechaHora;
import com.tallerwebi.dominio.excepcion.ElUsuarioYatieneTurnoConElProfesional;
import com.tallerwebi.dominio.excepcion.ProhibidoFechaAnteriorALaActual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public Turno agendarTurno(Long usuarioId, Long profesionalId, Date fechaHora) {
        List<Turno> turnosExistentesConMismoProfesional = repositorioTurno.traerTurnosActivosConProfesional(usuarioId, profesionalId);
        List<Turno> turnosExistentesEnMismaFechaHora = repositorioTurno.traerTurnosActivosEnHorario(usuarioId, fechaHora);
        boolean profesionalOcupado = repositorioTurno.profesionalTieneTurnoEnFechaHora(profesionalId, fechaHora);
        Date fechaHoraActual = new Date();

        if (!turnosExistentesConMismoProfesional.isEmpty()) {
            throw new ElUsuarioYatieneTurnoConElProfesional();
        } else if (!turnosExistentesEnMismaFechaHora.isEmpty()) {
            throw new ElUsuarioYaTieneTurnoEnEsaFechaHora();
        } else if (fechaHora.before(fechaHoraActual)) {
            throw new ProhibidoFechaAnteriorALaActual();
        } else if (profesionalOcupado) {
            throw new RuntimeException("El profesional ya tiene un turno en esta fecha y hora.");
        }

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

    @Override
    @Transactional
    public void actualizarEstadoTurno(Long turnoId, EstadoTurno nuevoEstado) {
        Turno turno = repositorioTurno.buscarPorId(turnoId);
                //.orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        if (turno == null) {
            throw new RuntimeException("Turno no encontrado");
        }

        turno.setEstado(nuevoEstado);
        repositorioTurno.actualizarTurno(turno);
    }

    @Override
    @Transactional
    public List<Turno> obtenerTurnosPorUsuario(Long usuarioId) {
        Usuario usuario = repositorioUsuario.buscarPorId(usuarioId);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return repositorioTurno.buscarTurnosPorUsuario(usuario);
    }

    @Override
    @Transactional
    public List<Turno> obtenerTurnosPorProfesional(String profesionalMail) {
        //Profesional profesional = repositorioProfesional.buscarProfesionalPorId(profesionalId);
        Profesional profesional = repositorioProfesional.buscarProfesionalPorEmail(profesionalMail);
        if (profesional == null) {
            throw new RuntimeException("Profesional no encontrado");
        }
        return repositorioTurno.buscarTurnosPorProfesional(profesional);
    }

    @Override
    @Transactional
    public void eliminarTurno(Long turnoId) {
        Turno turno = repositorioTurno.buscarPorId(turnoId);
        repositorioTurno.eliminar(turno);
    }
}
