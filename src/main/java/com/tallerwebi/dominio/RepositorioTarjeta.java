package com.tallerwebi.dominio;

public interface RepositorioTarjeta {

    void guardarTarjeta(Tarjeta tarjeta);
    Tarjeta obtenerTarjetaPorId(Long id);

}
