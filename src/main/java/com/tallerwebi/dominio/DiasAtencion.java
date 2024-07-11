package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class DiasAtencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesional_id")
    private Profesional profesional;

    @Enumerated(EnumType.STRING)
    private DiasSemana dia;

    private LocalTime hora_desde;
    private LocalTime hora_hasta;
    private Integer duracion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public DiasSemana getDia() {
        return dia;
    }

    public void setDia(DiasSemana dia) {
        this.dia = dia;
    }

    public LocalTime getHora_desde() {
        return hora_desde;
    }

    public void setHora_desde(LocalTime hora_desde) {
        this.hora_desde = hora_desde;
    }

    public LocalTime getHora_hasta() {
        return hora_hasta;
    }

    public void setHora_hasta(LocalTime hora_hasta) {
        this.hora_hasta = hora_hasta;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
}
