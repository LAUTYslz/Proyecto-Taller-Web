package com.tallerwebi.Dto;

import java.time.LocalTime;

public class DiasAtencionDTO {
    private Long idProfesional;
    private String diaSemana;
    private LocalTime horaDesde;
    private LocalTime horaHasta;
    private int duracionSesiones;

    public Long getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(Long idProfesional) {
        this.idProfesional = idProfesional;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraDesde() {
        return horaDesde;
    }

    public void setHoraDesde(LocalTime horaDesde) {
        this.horaDesde = horaDesde;
    }

    public LocalTime getHoraHasta() {
        return horaHasta;
    }

    public void setHoraHasta(LocalTime horaHasta) {
        this.horaHasta = horaHasta;
    }

    public int getDuracionSesiones() {
        return duracionSesiones;
    }

    public void setDuracionSesiones(int duracionSesiones) {
        this.duracionSesiones = duracionSesiones;
    }
}