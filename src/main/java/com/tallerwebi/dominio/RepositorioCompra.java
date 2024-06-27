package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CompraInexistente;

import java.util.List;

public interface RepositorioCompra {

    public Compra buscarCompraPorId(Long id);
    public List<Compra> getAllCompras();
    public void agregarCompra(Compra compra);
    public void eliminarCompra(Compra compra);
    public void actualizarCompra(Compra compra);
    Compra getCarritoByUser(Usuario usuario);
    List<Producto> getProductosDeCompra(Long idCompra);
}
