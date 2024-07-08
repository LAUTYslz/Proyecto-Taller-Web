package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity @Getter @Setter
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String imagenUrl;
    private Long stock;
    @ManyToOne (fetch = FetchType.EAGER)
    private Tienda tienda;
    @ManyToOne (fetch = FetchType.EAGER)
    private Etapa etapa;

    public Producto(){

    }


}
