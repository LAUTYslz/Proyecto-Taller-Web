package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoHayProductos;
import com.tallerwebi.dominio.excepcion.ProductoInexistente;
import com.tallerwebi.dominio.excepcion.StockInexistente;

import java.util.List;

public interface ServicioProducto {

    void guardarProducto(Producto producto);
    void actualizarProducto(Producto producto);
    void eliminarProducto(Long idProducto);
    Producto buscarProductoPorId(Long idProducto) throws ProductoInexistente;
    Long consultarStockPorProducto(Long idProducto) throws StockInexistente;
    void disminuirStockDeProducto(Long idProducto);
    List<Producto> obtenerProductosPorEtapa(Long idEtapa) throws NoHayProductos;
    List<Producto> listarProductos();

}
