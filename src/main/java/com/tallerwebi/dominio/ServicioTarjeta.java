package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.TarjetaInvalida;

public interface ServicioTarjeta {

    void guardarTarjeta(Tarjeta tarjeta);
    void validarTarjeta(Tarjeta tarjeta) throws TarjetaInvalida;
    Tarjeta obtenerTarjetaPorId(Long id);

}
