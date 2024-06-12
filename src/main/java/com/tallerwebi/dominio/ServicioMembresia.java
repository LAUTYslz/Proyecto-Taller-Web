package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

public interface ServicioMembresia {

    void darDeAltaMembresia(DatosMembresia datosMembresia) throws MembresiaExistente, TarjetaInvalida, CodigoInvalido, TarjetaVencida;
    void darDeBajaMembresia(String email) throws MembresiaInexistente;
    DatosMembresia buscarMembresia(String email) throws MembresiaInexistente;


}