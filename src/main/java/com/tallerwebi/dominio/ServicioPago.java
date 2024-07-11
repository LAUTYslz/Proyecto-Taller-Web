package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioPago {




    Pago generarPago(Profesional profesional, Consulta consulta, Caja caja);

    Pago obtenerPagoPorId(Long pagoId);

    List<Pago> obtenerListaPagos();
}
