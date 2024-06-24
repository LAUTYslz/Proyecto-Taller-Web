package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.CompraInexistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Service("servicioCompra")
@Transactional
public class ServicioCompraImpl implements ServicioCompra {
    private final RepositorioCompra repositorioCompra;

    @Autowired
    public ServicioCompraImpl(RepositorioCompra repositorioCompra) {
        this.repositorioCompra = repositorioCompra;
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
    public Boolean agregarProducto(Producto producto, Long idCompra) {
        Compra compra = repositorioCompra.buscarCompraPorId(idCompra);
        if (compra == null){
            return false;
        } else {
            compra.agregarProducto(producto);
        } return true;
    }

    @Override
    public Boolean eliminarProducto(Producto producto, Long idCompra) {
        Compra compra = repositorioCompra.buscarCompraPorId(idCompra);
        if (compra == null){
            return false;
        } else {
            compra.eliminarProducto(producto);
        } return true;
    }

    @Override
    public Boolean setearListaDeProductos(List<Producto> productos, Long idCompra) {
        Compra compra = repositorioCompra.buscarCompraPorId(idCompra);
        if (compra == null){
            return false;
        } else {
            compra.setProductos(productos);
        } return true;
    }

    @Override
    public Boolean finalizarCompra(Long id){
        Compra compra = repositorioCompra.buscarCompraPorId(id);
        if (compra == null) {
            return false;
        } else {
            compra.setEstado(EstadoCompra.REALIZADA);
        } return true;
    }

    @Override
    public Boolean cancelarCompra(Long id){
        Compra compra = repositorioCompra.buscarCompraPorId(id);
        if (compra == null) {
            return false;
        } else {
            compra.setEstado(EstadoCompra.CANCELADA);
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
        } return true;
    }

    @Override
    public Compra obtenerCompraActual(HttpServletRequest request) {
        return (Compra) request.getSession().getAttribute("compra");
    }


}
