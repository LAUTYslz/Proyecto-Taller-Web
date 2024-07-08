package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
@Entity
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   @ManyToOne
    private Profesional profesional;
    @ManyToOne
    private Consulta consulta;
    private LocalDate fechaPago;
    private Integer importeTotal;

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

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(Integer importeTotal) {
        this.importeTotal = importeTotal;
    }
}
