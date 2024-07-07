package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity @Getter @Setter
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Usuario usuario;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Producto> productos;

    private Double total;

    private EstadoCompra estado;

    private String direccion;

    @ManyToOne
    private Tarjeta tarjeta;

    public Compra(){
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compra compra = (Compra) o;
        return Objects.equals(id, compra.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
