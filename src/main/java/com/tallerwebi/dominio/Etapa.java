package com.tallerwebi.dominio;
import javax.persistence.*;
@Entity
public class Etapa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer desde;
    private Integer hasta;

    public Etapa(){

    }

    public Etapa(String s, int i, int i1) {
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
        this.nombre = nombre;
    }

    public Integer getDesde() {
        return desde;
    }

    public void setDesde(Integer desde) {
        this.desde = desde;
    }

    public Integer getHasta() {
        return hasta;
    }

    public void setHasta(Integer hasta) {
        this.hasta = hasta;
    }
}
