package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioMembresiaActivada")
@Transactional
public class ServicioMembresiaActivadaImpl implements ServicioMembresiaActivada {
    private final RepositorioMembresia repositorioMembresia;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioAdmi repositorioAdmi;
    private final RepositorioMembresiaActivada repositorioMembresiaActivada;

    public ServicioMembresiaActivadaImpl(RepositorioMembresia repositorioMembresia, RepositorioUsuario repositorioUsuario, RepositorioAdmi repositorioAdmi, RepositorioMembresiaActivada repositorioMembresiaActivada) {
        this.repositorioMembresia = repositorioMembresia;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioAdmi = repositorioAdmi;
        this.repositorioMembresiaActivada = repositorioMembresiaActivada;
    }


    @Override
    public Consulta realizarConsulta(Consulta consulta) {
        return repositorioMembresiaActivada.guardarConsulta(consulta);
    }
}
