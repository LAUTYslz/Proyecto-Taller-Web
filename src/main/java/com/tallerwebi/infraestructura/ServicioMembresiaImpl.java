package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

@Service("servicioMembresia")
@Transactional
public class ServicioMembresiaImpl implements ServicioMembresia {

    private final RepositorioMembresia repositorioMembresia;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioAdmi repositorioAdmi;

    public ServicioMembresiaImpl(RepositorioMembresia repositorioMembresia, RepositorioUsuario repositorioUsuario, RepositorioAdmi repositorioAdmi) {
        this.repositorioMembresia = repositorioMembresia;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioAdmi = repositorioAdmi;
    }

    @Override
    public void darDeAltaMembresia(DatosMembresia datosMembresia, Usuario usuario) throws MembresiaExistente, TarjetaInvalida, CodigoInvalido, UsuarioInexistente, FechadeInicioNoIngresada {
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


        // Obtener la fecha de hoy
        Date fechaDeHoy = new Date();
        // Asignar la fecha de hoy al método
        datosMembresia.setFechaDeInicio(fechaDeHoy);
       Date fechaDeBaja= fechaDeBaja(fechaDeHoy);
       datosMembresia.setFechaDeBaja(fechaDeBaja);
       // activo memebresia, le cambio el estado
       activarMembresia(datosMembresia);
       ingresaImporteACaja(datosMembresia);

        // Guardar la membresía
        repositorioMembresia.registrarMembresia(datosMembresia);
        // Asociar la membresía con el usuario
        usuarioMembresia.setMembresia(datosMembresia);
// auctialuzo usuario
        repositorioUsuario.actualizar(usuarioMembresia);

    }

    public void ingresaImporteACaja(DatosMembresia datosMembresia) {
        Caja caja = new Caja();
        caja.setIngreso(datosMembresia.getCuota());
    }

    public void activarMembresia(DatosMembresia membresia) {
        membresia.setCuota(5000);
        membresia.setEstado(Estado.valueOf("ACTIVADA"));
    }


    // Método para calcular la fecha de baja que es 3 meses después de la fecha de inicio
    private Date fechaDeBaja(Date fechaDeHoy) throws FechadeInicioNoIngresada {
        // Verificar que la fecha de inicio no sea nula (esto es importante para evitar NullPointerException)
        if (fechaDeHoy != null) {
            // Crear un objeto Calendar y establecer la fecha de hoy
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(fechaDeHoy);

            // Agregar 3 meses a la fecha de hoy
            calendario.add(Calendar.MONTH, 3);

            // Obtener la fecha de baja como un objeto Date
            Date fechaDeBaja = calendario.getTime();


            return fechaDeBaja;
        } else {
            throw new FechadeInicioNoIngresada();

        }
    }
    @Override
    public void desactivarMembresia(DatosMembresia membresia) throws MembresiaInexistente {
        DatosMembresia membresiaAActualizar = this.repositorioMembresia.buscarMembresia(membresia.getId());

        // Verificar si la membresía existe y la fecha de baja es posterior a la fecha actual
        if (membresiaAActualizar != null && esFechaDeBajaValida(membresiaAActualizar.getFechaDeBaja())) {
            membresiaAActualizar.setEstado(Estado.INACTIVA); // Cambiar estado a INACTIVA
            this.repositorioMembresia.actualizarMembresia(membresiaAActualizar); // Actualizar en el repositorio
        } else {
            throw new MembresiaInexistente();
        }
    }

    // Método para verificar si la fecha de baja es válida
    private boolean esFechaDeBajaValida(Date fechaDeBaja) {
        Date fechaActual = new Date(); // Obtener la fecha actual

        // Comparar la fecha de baja con la fecha actual
        return fechaDeBaja != null && fechaDeBaja.after(fechaActual);
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