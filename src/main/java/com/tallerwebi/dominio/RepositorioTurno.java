package com.tallerwebi.dominio;

import java.util.Date;
import java.util.List;

public interface RepositorioTurno {
    List<Turno> buscarTurnosPorUsuario(Usuario usuario);
    List<Turno> buscarTurnosPorProfesional(Profesional profesional);

    Turno guardarTurno(Turno turno);

    Turno buscarPorId(Long turnoId);

    void actualizarTurno(Turno turno);

    void eliminar(Turno turno);

    List<Turno> traerTurnosActivosConProfesional(Long usuarioId, Long profesionalId);

    List<Turno> traerTurnosActivosEnHorario(Long usuarioId, Date fechaHora);

    boolean profesionalTieneTurnoEnFechaHora(Long profesionalId, Date fechaHora);

    List<Turno> obtenerTurnosRealizadosPorProfesional(Long profesionalId);
}
