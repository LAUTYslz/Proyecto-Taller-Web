package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public Pago generarPago(Profesional profesional, List<Consulta> consultas, Integer importeTotal, Caja caja) {
        Pago pago = new Pago();
        pago.setProfesional(profesional);
        pago.setImporteTotal(importeTotal);
        pago.setConsulta(consultas.get(0));
        caja.setEgreso(importeTotal);
        return pago;
    }



}