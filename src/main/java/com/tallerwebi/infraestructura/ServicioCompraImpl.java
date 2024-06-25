package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.CompraInexistente;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public Compra buscarCompraPorId(Long id) throws CompraInexistente {
        Compra compraBuscada = repositorioCompra.buscarCompraPorId(id);
        if (compraBuscada == null) {
            throw new CompraInexistente();
        } return compraBuscada;
    }

    @Override
    public List<Compra> getAllCompras() {
        return repositorioCompra.getAllCompras();
    }

    @Override
    public void agregarCompra(Compra compra) {
        repositorioCompra.agregarCompra(compra);
    }

    @Override
    public void eliminarCompra(Compra compra) {
        repositorioCompra.eliminarCompra(compra);
    }

    @Override
    @Transactional
    public Boolean agregarProducto(Producto producto, Long idCompra) {
        Compra compra = repositorioCompra.buscarCompraPorId(idCompra);
        if (compra == null){
            return false;
        } else {
            compra.agregarProducto(producto);
            repositorioCompra.actualizarCompra(compra);
        } return true;
    }

    @Override
    public Boolean eliminarProducto(Producto producto, Long idCompra) {
        Compra compra = repositorioCompra.buscarCompraPorId(idCompra);
        if (compra == null){
            return false;
        } else {
            compra.eliminarProducto(producto);
            repositorioCompra.actualizarCompra(compra);
        } return true;
    }

    @Override
    public Boolean setearListaDeProductos(List<Producto> productos, Long idCompra) {
        Compra compra = repositorioCompra.buscarCompraPorId(idCompra);
        if (compra == null){
            return false;
        } else {
            compra.setProductos(productos);
            compra.setTotal(calcularPrecioDeProductos(productos));
            repositorioCompra.actualizarCompra(compra);
        } return true;
    }

    private Double calcularPrecioDeProductos(List<Producto> productos){
        Double precio = 0.0;

        for (Producto producto : productos){
            precio += producto.getPrecio();
        }
        return precio;
    }

    @Override
    public Boolean finalizarCompra(Long id){
        Compra compra = repositorioCompra.buscarCompraPorId(id);
        if (compra == null) {
            return false;
        } else {
            compra.setEstado(EstadoCompra.REALIZADA);
            repositorioCompra.actualizarCompra(compra);
        } return true;
    }

    @Override
    public Boolean cancelarCompra(Long id){
        Compra compra = repositorioCompra.buscarCompraPorId(id);
        if (compra == null) {
            return false;
        } else {
            compra.setEstado(EstadoCompra.CANCELADA);
            repositorioCompra.actualizarCompra(compra);
        } return true;
    }

    @Override
    public Boolean aplicarDescuento(Integer desc, Long idCompra) {
        Compra compra = repositorioCompra.buscarCompraPorId(idCompra);
        if (compra == null) {
            return false;
        } else {
            Double valorConDescuento = compra.getTotal() - (compra.getTotal()*desc);
            compra.setTotal(valorConDescuento);
            repositorioCompra.actualizarCompra(compra);
        } return true;
    }

    @Override
    @Transactional
    public Compra obtenerCompraActual(HttpServletRequest request) {
        Compra compra = (Compra) request.getSession().getAttribute("compra");

        if (compra != null){
            Hibernate.initialize(compra.getProductos());
        }

        HttpSession session = request.getSession();
        session.setAttribute("compra", compra);

        return compra;
    }

    @Override
    public void actualizarCompra(Compra compra) {
        repositorioCompra.actualizarCompra(compra);
    }

    @Override
    public Compra getCarritoByUser(Usuario usuario) {
        return repositorioCompra.getCarritoByUser(usuario);
    }


}
