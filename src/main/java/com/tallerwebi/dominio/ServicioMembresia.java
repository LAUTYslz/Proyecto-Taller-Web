package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.MembresiaExistente;
import com.tallerwebi.dominio.excepcion.MembresiaInexistente;
import com.tallerwebi.dominio.excepcion.TarjetaInvalida;
import com.tallerwebi.presentacion.DatosMembresia;

public interface ServicioMembresia {

 void darDeAltaMembresia(DatosMembresia datosMembresia) throws MembresiaExistente, TarjetaInvalida;
 void darDeBajaMembresia(String email) throws MembresiaInexistente;
 DatosMembresia buscarMembresia(String email) throws MembresiaInexistente;


}
