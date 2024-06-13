package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service("servicioMembresia")
@Transactional
public class ServicioMembresiaImpl implements ServicioMembresia {

    private final RepositorioMembresia repositorioMembresia;
    private final RepositorioUsuario repositorioUsuario;

    public ServicioMembresiaImpl(RepositorioMembresia repositorioMembresia, RepositorioUsuario repositorioUsuario) {
        this.repositorioMembresia = repositorioMembresia;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public void darDeAltaMembresia(DatosMembresia datosMembresia, Usuario usuario) throws MembresiaExistente, TarjetaInvalida, CodigoInvalido, UsuarioInexistente {
        if (!validarNumeroDeTarjeta(datosMembresia.getTarjeta().getNumeroDeTarjeta())){
            throw new TarjetaInvalida();
        }

        if (!validarCVV(datosMembresia.getTarjeta().getCodigoDeSeguridad())){
            throw new CodigoInvalido();
        }


        Usuario usuarioMembresia=repositorioUsuario.buscarPorId(usuario.getId());
        if(usuarioMembresia ==null){
            throw new UsuarioInexistente();
        }
        // Guardar la tarjeta en la base de datos
        repositorioMembresia.guardarTarjeta(datosMembresia.getTarjeta());

        // Asociar la tarjeta guardada con la membresía
        datosMembresia.setTarjeta(datosMembresia.getTarjeta());

        // Asociar la membresía con el usuario
        usuarioMembresia.setMembresia(datosMembresia);

        // Guardar la membresía
        repositorioMembresia.registrarMembresia(datosMembresia);
    }

    @Override
    public void darDeBajaMembresia(Long id) throws MembresiaInexistente {
        DatosMembresia membresiaAEliminar = this.repositorioMembresia.buscarMembresia(id);
        if (membresiaAEliminar != null) {
            repositorioMembresia.eliminarMembresia(membresiaAEliminar.getId());
        } else {
            throw new MembresiaInexistente();
        }
    }

    @Override
    public DatosMembresia buscarMembresia(Long id) throws MembresiaInexistente {
        return this.repositorioMembresia.buscarMembresia(id);
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