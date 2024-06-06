package com.tallerwebi.dominio;

import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.persistence.*;



public class Membresia {


    private Long id;
    private String nombreCompleto;
    private String email;
    private Long numeroTelefonico;



    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(Long numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }


}
