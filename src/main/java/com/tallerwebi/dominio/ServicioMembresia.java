package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CodigoInvalido;
import com.tallerwebi.dominio.excepcion.MembresiaExistente;
import com.tallerwebi.dominio.excepcion.MembresiaInexistente;
import com.tallerwebi.dominio.excepcion.TarjetaInvalida;
<<<<<<< HEAD
import com.tallerwebi.presentacion.DatosMembresia;

public interface ServicioMembresia {

 void darDeAltaMembresia(Membresia membresia) throws MembresiaExistente, TarjetaInvalida, CodigoInvalido;
=======

public interface ServicioMembresia {

 void darDeAltaMembresia(DatosMembresia datosMembresia) throws MembresiaExistente, TarjetaInvalida, CodigoInvalido;
>>>>>>> ana
 void darDeBajaMembresia(String email) throws MembresiaInexistente;
 DatosMembresia buscarMembresia(String email) throws MembresiaInexistente;


}
