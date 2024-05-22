package com.tallerwebi.dominio;

public class DatosMembresia {
    private String nombreCompleto;
    private String email;
    private Long numeroTelefonico;
    private Tarjeta tarjeta;

    public DatosMembresia(){

    }

    public DatosMembresia (String nombreCompleto, String email, Long numeroTelefonico, Tarjeta tarjeta){
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.numeroTelefonico = numeroTelefonico;
        this.tarjeta = tarjeta;
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

}
