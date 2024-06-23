package com.tallerwebi.dominio;

public interface ServicioTienda {

    public Tienda obtenerListadoDeTiendas();
    public String obtenerInfoTienda(Long id);
    public Boolean guardarTienda(Tienda tienda);
    public Boolean eliminarTienda(Long id);

}
