package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoHayProductos;
import com.tallerwebi.dominio.excepcion.ProductoInexistente;
import com.tallerwebi.dominio.excepcion.StockInexistente;

import java.util.List;

public interface ServicioProducto {

    Producto buscarProductoPorNombre(String nombre) throws ProductoInexistente;
    void agregarProducto(Producto producto);
    void actualizarProducto(Producto producto);
    void eliminarProducto(Long id);
    List<Producto> listarProductos() throws NoHayProductos;
    List<Producto> obtenerProductosPorEtapa(Long id) throws ProductoInexistente;
    Long consultarStockPorId(Long id) throws StockInexistente;
    Producto buscarProductoPorId(Long id) throws ProductoInexistente;
    Boolean disminuirStockDeProducto(Long id) throws ProductoInexistente;
}
