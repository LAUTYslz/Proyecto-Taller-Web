package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioAdmi;
import com.tallerwebi.dominio.RepositorioMembresia;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioMembresiaActivada;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioMembresiaActivada")
@Transactional
public class ServicioMembresiaActivadaImpl implements ServicioMembresiaActivada {
    private final RepositorioMembresia repositorioMembresia;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioAdmi repositorioAdmi;
    private final RepositorioMembresiaActivadaImpl repositorioMembresiaActivada;

    public ServicioMembresiaActivadaImpl(RepositorioMembresia repositorioMembresia, RepositorioUsuario repositorioUsuario, RepositorioAdmi repositorioAdmi, RepositorioMembresiaActivadaImpl repositorioMembresiaActivada) {
        this.repositorioMembresia = repositorioMembresia;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioAdmi = repositorioAdmi;
        this.repositorioMembresiaActivada = repositorioMembresiaActivada;
    }


}
