package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.StockInexistente;

import java.util.List;

public interface RepositorioProducto {

    void guardarProducto(Producto producto);
    void actualizarProducto(Producto producto);
    void eliminarProducto(Long idProducto);
    Producto buscarProductoPorId(Long idProducto);
    Long consultarStockPorProducto(Long idProducto);
    void disminuirStockDeProducto(Long idProducto);
    List<Producto> obtenerProductosPorEtapa(Long idEtapa);
    List<Producto> listarProductos();

}
