package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.CodigoInvalido;
import com.tallerwebi.dominio.excepcion.MembresiaExistente;
import com.tallerwebi.dominio.excepcion.MembresiaInexistente;
import com.tallerwebi.dominio.excepcion.TarjetaInvalida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Map;

@Service("servicioMembresia")
@Transactional
public class ServicioMembresiaImpl implements ServicioMembresia {

    private final RepositorioMembresia repositorioMembresia;

    @Autowired
    public ServicioMembresiaImpl(RepositorioMembresia repositorioMembresia) {
        this.repositorioMembresia = repositorioMembresia;
    }

    @Override
    public void darDeAltaMembresia(DatosMembresia datosMembresia) throws MembresiaExistente, TarjetaInvalida, CodigoInvalido{
        if (!validarNumeroDeTarjeta(datosMembresia.getTarjeta().getNumeroDeTarjeta())){
            throw new TarjetaInvalida();
        }

        if (!validarCVV(datosMembresia.getTarjeta().getCodigoDeSeguridad())){
            throw new CodigoInvalido();
        }

        if (repositorioMembresia.buscarMembresia(datosMembresia.getEmail()) != null){
            throw new MembresiaExistente();
        }
    }

    @Override
    public void darDeBajaMembresia(String email) throws MembresiaInexistente {
        DatosMembresia membresiaAEliminar = this.repositorioMembresia.buscarMembresia(email);
        if (membresiaAEliminar != null) {
            this.repositorioMembresia.eliminarMembresia(email);
        } else {
            throw new MembresiaInexistente();
        }
    }

    @Override
    public DatosMembresia buscarMembresia(String email) throws MembresiaInexistente {
        return this.repositorioMembresia.buscarMembresia(email);
    }

    private Boolean validarCVV(Integer cvv) throws CodigoInvalido {

        if (cvv == null){
            throw new CodigoInvalido();
        }

        int longitud = cvv.toString().length();

        if (longitud != 3){
            throw new CodigoInvalido();
        }

        String cvvString = cvv.toString();

        for (char c : cvvString.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new CodigoInvalido();
            }
        } return true;
    }

    private Boolean validarNumeroDeTarjeta(Long numeroDeTarjeta) throws TarjetaInvalida {

        if (numeroDeTarjeta == null){
            throw new TarjetaInvalida();
        }

        int longitud = numeroDeTarjeta.toString().length();

        if (longitud < 15 || longitud > 16){
            throw new TarjetaInvalida();
        }

        String numeroDeTarjetaString = numeroDeTarjeta.toString();

        for (char c : numeroDeTarjetaString.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new TarjetaInvalida();
            }
        }

        return true;

    }

}