package com.tallerwebi.dominio;

public class DatosMembresia {
    private String nombreCompleto;
    private String email;
    private Long numeroTelefonico;
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

    @Override
    public String toString() {
        return "DatosMembresia{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", email='" + email + '\'' +
                ", numeroTelefonico=" + numeroTelefonico +
                ", tarjeta=" + tarjeta +
                '}';
    }
}