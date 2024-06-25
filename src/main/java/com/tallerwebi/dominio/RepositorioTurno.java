package com.tallerwebi.dominio;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RepositorioTurno {
    List<Turno> buscarTurnosPorUsuario(Usuario usuario);
    List<Turno> buscarTurnosPorProfesional(Profesional profesional);

    Turno guardarTurno(Turno turno);

    Turno buscarPorId(Long turnoId);

    void actualizarTurno(Turno turno);

    void eliminar(Turno turno);

    List<Turno> traerTurnosActivosConProfesional(Long usuarioId, Long profesionalId);

    List<Turno> traerTurnosActivosEnHorario(Long usuarioId, Date fechaHora);

}
