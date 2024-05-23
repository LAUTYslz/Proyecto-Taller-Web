package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String rol;
    private String estado = "inactivo";
    private String nombre;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "conyuge_id", referencedColumnName = "id")
    private Usuario conyuge;
    private String emailConyuge;
    private String passwordConyuge;
    private String nombreConyuge;

    // Getters y Setters

    public String getEmailConyuge() {
        if (conyuge != null) {
            return conyuge.getEmail();
        } else {
            return null;
        }
    }

    public String getPasswordConyuge() {
        if (conyuge != null) {
            return conyuge.getPassword();
        } else {
            return null;
        }
    }

    public String getNombreConyuge() {
        if (conyuge != null) {
            return conyuge.getNombre();
        } else {
            return null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getConyuge() {
        return conyuge;
    }

    public void setConyuge(Usuario conyuge) {
        this.conyuge = conyuge;
    }

    public void setEmailConyuge(String emailConyuge) {
        this.emailConyuge = emailConyuge;
    }

    public void setPasswordConyuge(String passwordConyuge) {
        this.passwordConyuge = passwordConyuge;
    }

    public void setNombreConyuge(String nombreConyuge) {
        this.nombreConyuge = nombreConyuge;
    }
}
