package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

public interface ServicioMembresia {

    void darDeAltaMembresia(DatosMembresia datosMembresia, Usuario usuario) throws MembresiaExistente, TarjetaInvalida, CodigoInvalido, UsuarioInexistente, FechadeInicioNoIngresada;




    void desactivarMembresia(DatosMembresia membresia) throws MembresiaInexistente;

    DatosMembresia buscarMembresia(Long id) throws MembresiaInexistente;
}