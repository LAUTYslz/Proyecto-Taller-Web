package com.tallerwebi.dominio;


import javax.persistence.*;

@Entity
public class Metodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;


    public Metodo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(nombre != null){
            nombre = nombre.toUpperCase();
        }
        this.nombre = nombre;
    }
}
