package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.util.List;

public interface ServicioTurno {
    public void agendarTurno(Long usuarioId, Long turnoId);
    public List<Turno> obtenerTurnosPorUsuario(Long usuarioId);
    public List<Turno> obtenerTurnosPorProfesional(String profesionalMail);
    public void cancelarTurno(Long turnoId);
    public void actualizarEstadoTurno(Long turnoId, EstadoTurno estadoTurno);
    public void generarTurnos(LocalDate fechaInicio, LocalDate fechaFin);

    List<Turno> obtenerTurnosPorTipoProfesional(String nombreTipo);

    List<Turno> obtenerTurnosReservadosPorProfesional(String profesionalMail);
}
