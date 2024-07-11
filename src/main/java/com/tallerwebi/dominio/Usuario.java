package com.tallerwebi.dominio;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String rol;
    private Boolean estado ;// enum o boolean
    private String nombre;
    @OneToOne
    private DatosMembresia membresia;

    @OneToOne
    private Usuario conyuge;

    @OneToMany
    List<Compra> compras;


    // Getters y Setters


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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public DatosMembresia getMembresia() {
        return membresia;
    }

    public void setMembresia(DatosMembresia membresia) {
        this.membresia = membresia;
    }

    public Usuario getConyuge() {
        return conyuge;
    }

    public void setConyuge(Usuario conyuge) {
        this.conyuge = conyuge;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

