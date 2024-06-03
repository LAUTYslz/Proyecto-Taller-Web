package com.tallerwebi.dominio;

import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.persistence.*;

@Entity

public class Membresia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreCompleto;
    private String email;
    private Long numeroTelefonico;
    @ManyToOne
    private Tarjeta tarjeta;
    @ManyToOne
    private Usuario usuario;


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

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }
}
