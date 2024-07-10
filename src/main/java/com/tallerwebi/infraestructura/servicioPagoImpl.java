package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service("servicioPago")
@Transactional
public class servicioPagoImpl implements ServicioPago {

    private final RepositorioMembresia repositorioMembresia;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioAdmi repositorioAdmi;
    private final RepositorioMembresiaActivada repositorioMembresiaActivada;
    private final ServicioLogin servicioLogin;
    private final ServicioProfesional servicioProfesional;
    private final RepositorioPago repositorioPago;


    public servicioPagoImpl(RepositorioMembresia repositorioMembresia, RepositorioUsuario repositorioUsuario, RepositorioAdmi repositorioAdmi, RepositorioMembresiaActivada repositorioMembresiaActivada, ServicioLogin servicioLogin, ServicioProfesional servicioProfesional, RepositorioPago repositorioPago) {
        this.repositorioMembresia = repositorioMembresia;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioAdmi = repositorioAdmi;
        this.repositorioMembresiaActivada = repositorioMembresiaActivada;
        this.servicioLogin = servicioLogin;
        this.servicioProfesional = servicioProfesional;
        this.repositorioPago = repositorioPago;
    }

    @Override
    public Pago generarPago(Profesional profesional, Consulta consulta, Caja caja) {
        Pago pago = new Pago();
        pago.setProfesional(profesional);
        Integer consultapago = consulta.getPrecio();
        Integer  saldoActual= caja.getSaldoActual();
        pago.setImporteTotal(consultapago);
        pago.setConsulta(consulta);
        pago.setFechaPago(LocalDate.now());

        // Actualizar la caja
        // Agregar el pago a la lista de pagos de la caja
        caja.agregarPago(pago);


        caja.setSaldoActual(saldoActual);
        caja.setEgreso(consultapago);

        // Actualizar el saldo actua caja.calcularSaldoActual();

        // Guardar la caja actualizada en el repositorio administrativo
        repositorioAdmi.actualizarCaja(caja);
        consulta.setEstadoConsulta(EstadoConsulta.PAGADA);
        repositorioMembresiaActivada.actualizarConsulta(consulta);
        // Guardar el pago en el repositorio de pagos
        repositorioPago.agregraPago(pago); // Actualizar la caja en el repositorio administrativo


        return pago;
    }

    @Override
    public Pago obtenerPagoPorId(Long pagoId) {
        return repositorioPago.obtenerPago(pagoId);
    }

    @Override
    public List<Pago> obtenerListaPagos() {
        return repositorioPago.obtenerListaPagos();
    }


}