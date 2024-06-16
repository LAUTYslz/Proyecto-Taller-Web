package com.tallerwebi.dominio;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.Date;

import static com.tallerwebi.dominio.Estado.INACTIVA;

@Entity
public class DatosMembresia {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String nombreCompleto;
    private String email;
    @Enumerated(EnumType.STRING)
    private Estado estado=INACTIVA;
    private Long numeroTelefonico;
    @DateTimeFormat(pattern = "yyy-mm-dd")
    private Date fechaDeInicio;
    @DateTimeFormat(pattern = "yyy-mm-dd")
    private Date fechaDeBaja;
    @ManyToOne
    private Tarjeta tarjeta;

    public DatosMembresia(){

    }


    public Long getNumeroTelefonico(){
        return this.numeroTelefonico;
    }

    public void setNumeroTelefonico(Long numeroTelefonico){
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getNombreCompleto() {
        return this.nombreCompleto;
    }

    public String getEmail(){
        return this.email;
    }
    public Tarjeta getTarjeta(){
        return this.tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta){
        this.tarjeta = tarjeta;
    }

    public void setNombreCompleto(String nombreCompleto){
        this.nombreCompleto = nombreCompleto;
    }

    public void setEmail(String email){
        this.email = email;
    }



    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Date getFechaDeInicio() {
        return fechaDeInicio;
    }

    public void setFechaDeInicio(Date fechaDeInicio) {
        this.fechaDeInicio = fechaDeInicio;
    }

    public Date getFechaDeBaja() {
        return fechaDeBaja;
    }

    public void setFechaDeBaja(Date fechaDeBaja) {
        this.fechaDeBaja = fechaDeBaja;
    }

    @Override
    public String toString() {
        return "DatosMembresia{" +
                "id=" + id +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", email='" + email + '\'' +
                ", estado=" + estado +
                ", numeroTelefonico=" + numeroTelefonico +
                ", fechaDeInicio=" + fechaDeInicio +
                ", fechaDeBaja=" + fechaDeBaja +
                ", tarjeta=" + tarjeta +
                '}';
    }
}

