package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.RepositorioTarjeta;
import com.tallerwebi.dominio.ServicioTarjeta;
import com.tallerwebi.dominio.Tarjeta;
import com.tallerwebi.dominio.excepcion.TarjetaInvalida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioTarjeta")
@Transactional
public class ServicioTarjetaImpl implements ServicioTarjeta {

    private RepositorioTarjeta repositorioTarjeta;

    @Autowired
    public ServicioTarjetaImpl(RepositorioTarjeta repositorioTarjeta) {
        this.repositorioTarjeta = repositorioTarjeta;
    }

    @Override
    public void guardarTarjeta(Tarjeta tarjeta) {
        this.repositorioTarjeta.guardarTarjeta(tarjeta);
    }

    @Override
    public void validarTarjeta(Tarjeta tarjeta) throws TarjetaInvalida {

        if (!validarLongitud(tarjeta.getNumeroDeTarjeta()) || !validarCVV(tarjeta.getCodigoDeSeguridad())) {
            throw new TarjetaInvalida();
        }
    }

    private Boolean validarCVV(Integer cvv){
        if (cvv == null){
            return false;
        }

        int longitud = cvv.toString().length();

        if (longitud != 3){
            return false;
        }

        String cvvString = cvv.toString();

        for (char c : cvvString.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        } return true;
    }

    private Boolean validarLongitud(Long numeroDeTarjeta){
        if (numeroDeTarjeta == null){
            return false;
        }

        int longitud = numeroDeTarjeta.toString().length();

        if (longitud < 15 || longitud > 16){
            return false;
        }

        String numeroDeTarjetaString = numeroDeTarjeta.toString();

        for (char c : numeroDeTarjetaString.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Tarjeta obtenerTarjetaPorId(Long id) {
        return null;
    }
}
