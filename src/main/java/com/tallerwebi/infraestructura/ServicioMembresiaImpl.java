package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioMembresia;
import com.tallerwebi.dominio.excepcion.MembresiaExistente;
import com.tallerwebi.dominio.excepcion.MembresiaInexistente;
import com.tallerwebi.dominio.excepcion.TarjetaInvalida;
import com.tallerwebi.presentacion.DatosMembresia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service("servicioMembresia")
@Transactional
public class ServicioMembresiaImpl implements ServicioMembresia {

    private ServicioMembresia servicioMembresia;
    private Map<String, DatosMembresia> membresias;

    @Override
    public void darDeAltaMembresia(DatosMembresia datosMembresia) throws MembresiaExistente, TarjetaInvalida{

        if (!validarNumeroDeTarjeta(datosMembresia.getTarjeta().getNumeroDeTarjeta())){
            throw new TarjetaInvalida();
        }

        if (buscarMembresia(datosMembresia.getEmail()) != null) {
            throw new MembresiaExistente();
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
        DatosMembresia membresiaBuscada = membresias.get(email);
        if (membresiaBuscada == null){
            return null;
        } return membresiaBuscada;
    }

    private Boolean validarNumeroDeTarjeta(Long numeroDeTarjeta) throws TarjetaInvalida {
        Integer longitud = numeroDeTarjeta.toString().length();
        if (longitud == 15 || longitud == 16){
            return true;
        } throw new TarjetaInvalida();
    }


}
