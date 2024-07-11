package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
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


    }

