package com.tallerwebi.dominio;
import java.util.List;

public interface RepositorioTienda {

    List<Tienda> obtenerListadoDeTiendas();
    Tienda obtenerTiendaPorId(Long id);
    void guardarTienda(Tienda tienda);
    void eliminarTienda(Long id);
    void actualizarTienda(Tienda tienda);

}
