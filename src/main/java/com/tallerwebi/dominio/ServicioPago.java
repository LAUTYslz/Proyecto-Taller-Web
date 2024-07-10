package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioPago {
    Pago generarPago(Profesional profesional, List<Consulta> consultas, Integer importeTotal, Caja caja);
}
