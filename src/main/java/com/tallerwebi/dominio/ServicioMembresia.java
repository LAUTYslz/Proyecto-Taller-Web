package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CodigoInvalido;
import com.tallerwebi.dominio.excepcion.MembresiaExistente;
import com.tallerwebi.dominio.excepcion.MembresiaInexistente;
import com.tallerwebi.dominio.excepcion.TarjetaInvalida;
import com.tallerwebi.presentacion.DatosMembresia;

public interface ServicioMembresia {

 void darDeAltaMembresia(Membresia membresia) throws MembresiaExistente, TarjetaInvalida, CodigoInvalido;
 void darDeBajaMembresia(String email) throws MembresiaInexistente;
 DatosMembresia buscarMembresia(String email) throws MembresiaInexistente;


}
