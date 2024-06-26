package com.tallerwebi.dominio;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    private Integer cantidad=0;
    private Integer mes;
    private Integer anio;

    @Enumerated(EnumType.STRING)
    private Mensaje estado;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;

    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Profesional profesional;

    @ManyToOne
    private Hijo hijo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public Hijo getHijo() {
        return hijo;
    }

    public void setHijo(Hijo hijo) {
        this.hijo = hijo;
    }


    public Mensaje getEstado(Mensaje sinLeer) {
        return estado;
    }

    public void setEstado(Mensaje estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Mensaje getEstado() {
        return estado;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", mensaje='" + mensaje + '\'' +
                ", cantidad=" + cantidad +
                ", mes=" + mes +
                ", anio=" + anio +
                ", estado=" + estado +
                ", fecha=" + fecha +
                ", usuario=" + usuario +
                ", profesional=" + profesional +
                ", hijo=" + hijo +
                '}';
    }
}
