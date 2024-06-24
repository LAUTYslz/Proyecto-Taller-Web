package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioProducto {

    Producto buscarProductoPorNombre(String nombre);
    void agregarProducto(Producto producto);
    void actualizarProducto(Producto producto);
    void eliminarProducto(Long id);
    List<Producto> listarProductos();
    List<Producto> obtenerProductosPorEtapa(Long id);
    Long consultarStockPorId(Long id);
    Producto obtenerInfoProducto(Long id);

}
