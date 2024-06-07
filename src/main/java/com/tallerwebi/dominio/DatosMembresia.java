package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class DatosMembresia {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String nombreCompleto;
    private String email;
    private Long numeroTelefonico;
    @ManyToOne
    private Tarjeta tarjeta;

    public DatosMembresia(){

    }

    public DatosMembresia(String nombreCompleto, String email, Long numeroTelefonico, Tarjeta tarjeta){
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.numeroTelefonico = numeroTelefonico;
        this.tarjeta = new Tarjeta(tarjeta.getNumeroDeTarjeta(), tarjeta.getFechaDeVencimiento(), tarjeta.getCodigoDeSeguridad());
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

    @Override
    public String toString() {
        return "DatosMembresia{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", email='" + email + '\'' +
                ", numeroTelefonico=" + numeroTelefonico +
                ", tarjeta=" + tarjeta +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}