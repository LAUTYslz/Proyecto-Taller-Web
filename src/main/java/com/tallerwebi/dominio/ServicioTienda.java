package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioTienda {

    List<Tienda> obtenerListadoDeTiendas();
    String obtenerInfoTienda(Long id);
    void guardarTienda(Tienda tienda);
    void eliminarTienda(Long id);

}
