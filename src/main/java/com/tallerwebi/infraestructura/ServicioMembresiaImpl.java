package com.tallerwebi.infraestructura;

<<<<<<< HEAD
import com.tallerwebi.dominio.Membresia;
import com.tallerwebi.dominio.RepositorioMembresia;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioMembresia;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.presentacion.DatosMembresia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
=======
import com.tallerwebi.dominio.ServicioMembresia;
import com.tallerwebi.dominio.excepcion.CodigoInvalido;
import com.tallerwebi.dominio.excepcion.MembresiaExistente;
import com.tallerwebi.dominio.excepcion.MembresiaInexistente;
import com.tallerwebi.dominio.excepcion.TarjetaInvalida;
import com.tallerwebi.dominio.DatosMembresia;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Map;
>>>>>>> ana

@Service("servicioMembresia")
@Transactional
public class ServicioMembresiaImpl implements ServicioMembresia {

<<<<<<< HEAD
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
=======
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
>>>>>>> ana
    }

    @Override
    public void darDeBajaMembresia(String email) throws MembresiaInexistente {
<<<<<<< HEAD

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
=======
>>>>>>> ana
        DatosMembresia membresiaAEliminar = buscarMembresia(email);

        if (membresiaAEliminar == null){
            throw new MembresiaInexistente();
        } membresias.remove(email);
    }

    @Override
    public DatosMembresia buscarMembresia(String email) {
        return membresias.get(email);
<<<<<<< HEAD
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



=======
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
>>>>>>> ana
