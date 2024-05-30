package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioMembresia;
import com.tallerwebi.dominio.excepcion.CodigoInvalido;
import com.tallerwebi.dominio.excepcion.MembresiaExistente;
import com.tallerwebi.dominio.excepcion.MembresiaInexistente;
import com.tallerwebi.dominio.excepcion.TarjetaInvalida;
import com.tallerwebi.dominio.DatosMembresia;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Map;

@Service("servicioMembresia")
@Transactional
public class ServicioMembresiaImpl implements ServicioMembresia {

    private Map<String, DatosMembresia> membresias;

    @Override
    public void darDeAltaMembresia(DatosMembresia datosMembresia) throws MembresiaExistente, TarjetaInvalida, CodigoInvalido{

        if (!validarNumeroDeTarjeta(datosMembresia.getTarjeta().getNumeroDeTarjeta())){
            throw new TarjetaInvalida();
        }

        if (buscarMembresia(datosMembresia.getEmail()) != null) {
            throw new MembresiaExistente();
        }

        if (validarCodigoDeSeguridad(datosMembresia.getTarjeta().getCodigoDeSeguridad())){
            throw new CodigoInvalido();
        }

        membresias.put(datosMembresia.getEmail(), datosMembresia);
    }

    @Override
    public void darDeBajaMembresia(String email) throws MembresiaInexistente {
        DatosMembresia membresiaAEliminar = buscarMembresia(email);

        if (membresiaAEliminar == null){
            throw new MembresiaInexistente();
        } membresias.remove(email);
    }

    @Override
    public DatosMembresia buscarMembresia(String email) {
        return membresias.get(email);
    }

    private Boolean validarNumeroDeTarjeta(Long numeroDeTarjeta) throws TarjetaInvalida {

        if (numeroDeTarjeta == null){
            throw new TarjetaInvalida();
        }

        int longitud = numeroDeTarjeta.toString().length();
        if (longitud < 15 || longitud > 16){
            throw new TarjetaInvalida();
        } return true;
    }

    private Boolean validarCodigoDeSeguridad(Integer codigo) throws CodigoInvalido {
        if (codigo == null){
            throw new CodigoInvalido();
        }
        int longitud = codigo.toString().length();
        if (longitud != 3){
            throw new CodigoInvalido();
        } return true;
    }


}