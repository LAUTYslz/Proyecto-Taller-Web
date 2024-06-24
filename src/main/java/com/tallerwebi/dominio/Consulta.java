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

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", mensaje='" + mensaje + '\'' +
                ", estado=" + estado +
                ", fecha=" + fecha +
                ", usuario=" + usuario +
                ", profesional=" + profesional +
                ", hijo=" + hijo +
                '}';
    }
}
