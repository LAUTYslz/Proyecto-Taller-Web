package com.tallerwebi.Infraestructura;

import com.tallerwebi.dominio.RepositorioMetodo;
import com.tallerwebi.dominio.ServicioMetodo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioMetodoImpl implements ServicioMetodo {
    private final RepositorioMetodo repositorioMetodo;

    @Autowired
    public ServicioMetodoImpl(RepositorioMetodo repositorioMetodo) {
        this.repositorioMetodo = repositorioMetodo;
    }
}
