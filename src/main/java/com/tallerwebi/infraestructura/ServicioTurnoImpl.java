package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ServicioTurnoImpl implements ServicioTurno {
    private final RepositorioTurno repositorioTurno;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioProfesional repositorioProfesional;
    private final RepositorioDiasAtencion repositorioDiasAtencion;

    @Autowired
    public ServicioTurnoImpl(RepositorioTurno repositorioTurno, RepositorioUsuario repositorioUsuario, RepositorioProfesional repositorioProfesional, RepositorioDiasAtencion repositorioDiasAtencion) {
        this.repositorioTurno = repositorioTurno;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioProfesional = repositorioProfesional;

        this.repositorioDiasAtencion = repositorioDiasAtencion;
    }

    @Override
    @Transactional
    public void agendarTurno(Long usuarioId, Long turnolId) {
        Date fechaHoraActual = new Date();

        Usuario usuario = repositorioUsuario.buscarPorId(usuarioId);
                //.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Turno turno = repositorioTurno.buscarPorId(turnolId);
        turno.setUsuario(usuario);
        repositorioTurno.actualizarTurno(turno);
    }

    @Override
    @Transactional
    public void actualizarEstadoTurno(Long turnoId, EstadoTurno nuevoEstado) {
        Turno turno = repositorioTurno.buscarPorId(turnoId);
                //.orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        if (turno == null) {
            throw new RuntimeException("Turno no encontrado");
        }

        //turno.setEstado(nuevoEstado);
        repositorioTurno.actualizarTurno(turno);
    }


    @Override
    @Transactional
    public void generarTurnos(LocalDate fechaInicio, LocalDate fechaFin) {
        repositorioTurno.eliminarTurnos();
        List<Profesional> profesionales = repositorioProfesional.traerProfesionales();


        for(Profesional profesional : profesionales) {
            if(profesional.getDiaAtencion() != null){
                LocalDate fechaActual = fechaInicio;

                while(!fechaActual.isAfter(fechaFin)){
                    int diaSemanaActual = fechaActual.getDayOfWeek().getValue();

                    if(profesional.getDiaAtencion().getValorEnum() == diaSemanaActual){
                        LocalTime horaActual = profesional.getHoraDesde();

                        while (horaActual.isBefore(profesional.getHoraHasta())) {
                            Turno turno = new Turno();
                            turno.setProfesional(profesional);
                            turno.setFecha(fechaActual);
                            turno.setHora(horaActual);

                            repositorioTurno.guardarTurno(turno);

                            horaActual = horaActual.plusMinutes(profesional.getDuracionSesiones());
                        }
                    }
                    fechaActual = fechaActual.plusDays(1);
                }
            }
        }
        List<Turno> turnos = repositorioTurno.traerTurnos();
        if(turnos.isEmpty()){
            throw new RuntimeException("Los Turnos no se generaron correctamente");
        }
    }

    @Override
    @Transactional
    public List<Turno> obtenerTurnosPorTipoProfesional(String nombreTipo) {
        if (nombreTipo != null && nombreTipo.isEmpty()) {
            throw  new RuntimeException("Debe seleccionar una especialidad");
        }
        List<Turno> turnos = repositorioTurno.obtenerTurnosPorEspecialidad(nombreTipo);
        return turnos != null ? turnos : new ArrayList<>(); // Retorna una lista vacía si `turnos` es `null`
    }

    @Override
    @Transactional
    public List<Turno> obtenerTurnosReservadosPorProfesional(String profesionalMail) {
        Profesional profesional = repositorioProfesional.buscarProfesionalPorEmail(profesionalMail);
        if (profesional == null) {
            throw new RuntimeException("Profesional no encontrado");
        }
        return repositorioTurno.buscarTurnosReservadosPorProfesional(profesional);
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
    public void cancelarTurno(Long turnoId) {
        Turno turno = repositorioTurno.buscarPorId(turnoId);
        if(turno == null){
            throw new RuntimeException("Turno no encontrado");
        }
        turno.setUsuario(null);
        repositorioTurno.guardarTurno(turno);
    }
}
