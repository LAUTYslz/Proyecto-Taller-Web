package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
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
    @JoinTable(
            name = "compra_producto",
            joinColumns = @JoinColumn(name = "compra_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos;

    private Double total;

    private EstadoCompra estado;

    private String direccion;


    public Compra(){
        this.productos = new ArrayList<>();
        this.total = 0.0;
        this.estado = EstadoCompra.PENDIENTE;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;

        for (Producto producto : productos) {
            this.total += producto.getPrecio();
        }

    }

    public void agregarProducto(Producto producto) {
        this.total += producto.getPrecio();
        this.productos.add(producto);
    }

    public void eliminarProducto(Producto producto) {
        this.total -= producto.getPrecio();
        this.productos.remove(producto);
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", productos=" + productos +
                ", total=" + total +
                ", estado=" + estado +
                '}';
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
