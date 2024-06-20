package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service("servicioMetodo")
@Transactional
public class ServicioMetodoImpl implements ServicioMetodo {
    private final RepositorioMetodo repositorioMetodo;
    private final ServicioLogin servicioLogin;
    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioMetodoImpl(RepositorioMetodo repositorioMetodo, ServicioLogin servicioLogin, RepositorioUsuario repositorioUsuario) {
        this.repositorioMetodo = repositorioMetodo;
        this.servicioLogin = servicioLogin;
        this.repositorioUsuario = repositorioUsuario;
    }


    @Override
    public List<Metodo> buscarMetodos() {
        return repositorioMetodo.buscarMetodos();
    }

    @Override
    public Hijo asociarHijo(Long hijoId, Long metodoId) {
        Hijo hijobuscardo= servicioLogin.busquedahijo(hijoId);
        Metodo metodobuscado= repositorioMetodo.buscarMetodoPorId(metodoId);
        hijobuscardo.setMetodo(metodobuscado);
        repositorioUsuario.actualizarHijo(hijobuscardo);
 return hijobuscardo;
    }
}
