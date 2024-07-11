package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPago {

    void agregraPago(Pago pago);

    Pago obtenerPago(Long pagoId);

    List<Pago> obtenerListaPagos();
}
