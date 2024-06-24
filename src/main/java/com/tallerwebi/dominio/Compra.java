package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Usuario usuario;

    @OneToMany
    private List<Producto> productos = new ArrayList<Producto>();

    private Double total;

    private EstadoCompra estado;

    public Compra(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Producto> getProductos() {
        return productos;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public EstadoCompra getEstado() {
        return estado;
    }

    public void setEstado(EstadoCompra estado) {
        this.estado = estado;
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
}
