package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

import java.util.List;

public interface ServicioCompra {

    Compra buscarCompra(Long id) throws CompraInexistente;
    void agregarCompra(Compra compra);
    void actualizarCompra(Compra compra);
    void eliminarCompra(Compra compra);
    List<Producto> obtenerProductosDeCompra(Long id) throws NoHayProductos;
    List<Compra> listarCompras();
    List<Compra> obtenerHistorialDeComprasPorUsuario(Usuario usuario) throws HistorialInexistente;
    Compra obtenerCarritoPorUsuario(Usuario usuario);
    Compra iniciarCompra(Long id);
    void agregarProductoACompra(Producto producto, Long idCompra) throws CompraInexistente;
    void eliminarProductoACompra(Producto producto, Long idCompra) throws CompraInexistente;

}
