package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CompraInexistente;

import java.util.List;

public interface RepositorioCompra {

    Compra buscarCompra(Long id);
    void agregarCompra(Compra compra);
    void actualizarCompra(Compra compra);
    void eliminarCompra(Compra compra);
    List<Compra> listarCompras();
    List<Compra> obtenerHistorialDeComprasPorUsuario(Usuario usuario);
    Compra obtenerCarritoPorUsuario(Usuario usuario);
}
