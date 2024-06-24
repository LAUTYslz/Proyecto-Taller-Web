package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.List;

@Entity
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String telefono;
    private String email;
    @OneToMany
    private List<Producto> productos;

    public Tienda() {

    }

    public Tienda(String nombre, String telefono, String email) {
        this.nombre = nombre;
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
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<Producto> getProductos() {
        return productos;
    }
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void addProducto(Producto producto) {
        this.productos.add(producto);
    }

    public void removeProducto(Producto producto) {
        this.productos.remove(producto);
    }

    @Override
    public String toString() {
        return "Tienda{" +
                "Nombre ='" + nombre + '\'' +
                ", Teléfono ='" + telefono + '\'' +
                ", Email ='" + email + '\'' +
                ", Productos =" + productos +
                '}';
    }
}
