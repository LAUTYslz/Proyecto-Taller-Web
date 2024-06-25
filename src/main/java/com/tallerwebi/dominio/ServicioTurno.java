package com.tallerwebi.dominio;

import java.util.Date;
import java.util.List;

public interface ServicioTurno {
    public Turno agendarTurno(Long usuarioId, Long profesionalId, Date fechaHora);
    public List<Turno> obtenerTurnosPorUsuario(Long usuarioId);
    public List<Turno> obtenerTurnosPorProfesional(String profesionalMail);
    public void eliminarTurno(Long turnoId);
    public void actualizarEstadoTurno(Long turnoId, EstadoTurno estadoTurno);
}
