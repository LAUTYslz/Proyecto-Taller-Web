package com.tallerwebi.dominio;
import com.tallerwebi.dominio.enums.Vacuna;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Vacunacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Hijo hijo;

    @Enumerated(EnumType.STRING)
    private Vacuna vacuna;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_administracion")
    private Date fechaAdministracion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hijo getHijo() {
        return hijo;
    }

    public void setHijo(Hijo hijo) {
        this.hijo = hijo;
    }

    public Vacuna getVacuna() {
        return vacuna;
    }

    public void setVacuna(Vacuna vacuna) {
        this.vacuna = vacuna;
    }

    public Date getFechaAdministracion() {
        return fechaAdministracion;
    }

    public void setFechaAdministracion(Date fechaAdministracion) {
        this.fechaAdministracion = fechaAdministracion;
    }
}