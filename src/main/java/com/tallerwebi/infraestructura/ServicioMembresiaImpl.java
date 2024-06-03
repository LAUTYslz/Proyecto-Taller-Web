package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Membresia;
import com.tallerwebi.dominio.RepositorioMembresia;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioMembresia;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.presentacion.DatosMembresia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service("servicioMembresia")
@Transactional
public class ServicioMembresiaImpl implements ServicioMembresia {

    private RepositorioMembresia repositorioMembresia;

    @Autowired
    public ServicioMembresiaImpl(RepositorioMembresia repositorioMembresia) {
        this.repositorioMembresia = repositorioMembresia;
    }


    @Override
    public void darDeAltaMembresia(Membresia membresia) throws MembresiaExistente, TarjetaInvalida, CodigoInvalido {
        if (!validarNumeroDeTarjeta(membresia.getTarjeta().getNumeroDeTarjeta())) {
            throw new TarjetaInvalida();
        }
        if (validarCodigoDeSeguridad(membresia.getTarjeta().getCodigoDeSeguridad())) {
            throw new CodigoInvalido();
        }

        repositorioMembresia.guardarMembresia(membresia);
    }

    @Override
    public void darDeBajaMembresia(String email) throws MembresiaInexistente {

    }

    @Override
    public DatosMembresia buscarMembresia(String email) throws MembresiaInexistente {
        return null;
    }

    private boolean validarCodigoDeSeguridad(Integer codigoDeSeguridad) throws CodigoInvalido {
        if (codigoDeSeguridad == null) {
            throw new CodigoInvalido();
        }
        int longitud = codigoDeSeguridad.toString().length();
        if (longitud != 3) {
            throw new CodigoInvalido();
        }
        return true;
    }







  /*  @Override
    public void darDeBajaMembresia(String email) throws MembresiaInexistente {
        DatosMembresia membresiaAEliminar = buscarMembresia(email);

        if (membresiaAEliminar == null){
            throw new MembresiaInexistente();
        } membresias.remove(email);
    }

    @Override
    public DatosMembresia buscarMembresia(String email) {
        return membresias.get(email);
    }*/

    private Boolean validarNumeroDeTarjeta(Long numeroDeTarjeta) throws TarjetaInvalida {

        if (numeroDeTarjeta == null) {
            throw new TarjetaInvalida();
        } else {

            int longitud = numeroDeTarjeta.toString().length();
            if (longitud < 15 || longitud > 16) {
                throw new TarjetaInvalida();
            }
            return true;
        }

    }

}



