package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.CompraInexistente;
import com.tallerwebi.dominio.excepcion.HistorialInexistente;
import com.tallerwebi.dominio.excepcion.NoHayProductos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("servicioCompra")
@Transactional
public class ServicioCompraImpl implements ServicioCompra {

    private final RepositorioCompra repositorioCompra;
    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioCompraImpl(RepositorioCompra repositorioCompra, RepositorioUsuario repositorioUsuario) {
        this.repositorioCompra = repositorioCompra;
        this.repositorioUsuario = repositorioUsuario;
    }


    @Override
    public Compra buscarCompra(Long id) throws CompraInexistente {
        if (this.repositorioCompra.buscarCompra(id) != null){
            return this.repositorioCompra.buscarCompra(id);
        } else {
            throw new CompraInexistente();
        }
    }

    @Override
    public void agregarCompra(Compra compra) {
        this.repositorioCompra.agregarCompra(compra);
    }

    @Override
    public void actualizarCompra(Compra compra) {
        this.repositorioCompra.actualizarCompra(compra);
    }

    @Override
    public void eliminarCompra(Compra compra) {
        this.repositorioCompra.eliminarCompra(compra);
    }

    @Override
    public List<Producto> obtenerProductosDeCompra(Long id) throws NoHayProductos {
        if (this.repositorioCompra.buscarCompra(id).getProductos() != null){
            return this.repositorioCompra.buscarCompra(id).getProductos();
        } else{
            throw new NoHayProductos();
        }
    }

    @Override
    public List<Compra> listarCompras() {
        return this.repositorioCompra.listarCompras();
    }

    @Override
    public List<Compra> obtenerHistorialDeComprasPorUsuario(Usuario usuario) throws HistorialInexistente {
        if (this.repositorioCompra.obtenerHistorialDeComprasPorUsuario(usuario) != null){
            return this.repositorioCompra.obtenerHistorialDeComprasPorUsuario(usuario);
        } else {
            throw new HistorialInexistente();
        }
    }

    @Override
        public Compra obtenerCarritoPorUsuario(Usuario usuario){
            return this.repositorioCompra.obtenerCarritoPorUsuario(usuario);
    }

    @Transactional
    @Override
    public Compra iniciarCompra(Long id) {
        Compra compra = new Compra();
        compra.setTotal(0.0);
        compra.setProductos(new ArrayList<>());
        compra.setUsuario(repositorioUsuario.buscarPorId(id));
        compra.setEstado(EstadoCompra.PENDIENTE);
        return compra;
    }

    @Transactional
    @Override
    public void agregarProductoACompra(Producto producto, Long idCompra) throws CompraInexistente {

        Compra compra = buscarCompra(idCompra);
        if (compra != null) {
            compra.getProductos().add(producto);
            compra.setTotal(compra.getTotal() + producto.getPrecio());
            repositorioCompra.actualizarCompra(compra);
        } else {
            throw new CompraInexistente();
        }

    }

    @Override
    public void eliminarProductoACompra(Producto producto, Long idCompra) throws CompraInexistente {

        Compra compra = buscarCompra(idCompra);
        if (compra != null) {
            compra.getProductos().remove(producto);
            compra.setTotal(compra.getTotal() - producto.getPrecio());
            repositorioCompra.actualizarCompra(compra);
        } else {
            throw new CompraInexistente();
        }
    }

    @Override
    public void asociarTarjetaACompra(Long idCompra, Tarjeta tarjeta) throws CompraInexistente {
        Compra compra = buscarCompra(idCompra);
        if (compra != null) {
            compra.setTarjeta(tarjeta);
            repositorioCompra.actualizarCompra(compra);
        } else {
            throw new CompraInexistente();
        }
    }

    @Override
    public void asociarDireccionACompra(Long idCompra, String direccion) throws CompraInexistente {
        Compra compra = buscarCompra(idCompra);
        if (compra != null) {
            compra.setDireccion(direccion);
            repositorioCompra.actualizarCompra(compra);
        } else {
            throw new CompraInexistente();
        }
    }

    @Override
    public void marcarComoRealizada(Long idCompra) throws CompraInexistente {
        Compra compra = buscarCompra(idCompra);

        if (compra != null) {
            compra.setEstado(EstadoCompra.REALIZADA);
            repositorioCompra.actualizarCompra(compra);
        } else {
            throw new CompraInexistente();
        }
    }

}
