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
    private String respuesta;
    private Integer precio=1000;
    @Enumerated(EnumType.STRING)
    private EstadoConsulta estadoConsulta;
    @Enumerated(EnumType.STRING)
    private Mensaje estado;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
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

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public EstadoConsulta getEstadoConsulta() {
        return estadoConsulta;
    }

    public void setEstadoConsulta(EstadoConsulta estadoConsulta) {
        this.estadoConsulta = estadoConsulta;
    }
}
