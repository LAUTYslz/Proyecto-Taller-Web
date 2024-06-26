package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Metodo;
import com.tallerwebi.dominio.RepositorioMetodo;
import com.tallerwebi.dominio.ServicioMetodo;
import com.tallerwebi.dominio.excepcion.MetodoNoEncontradoPorID;
import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioMetodoImpl implements ServicioMetodo {
    private final RepositorioMetodo repositorioMetodo;
    private final ServicioLogin servicioLogin;

    @Autowired
    public ServicioMetodoImpl(RepositorioMetodo repositorioMetodo, ServicioLogin servicioLogin) {
        this.repositorioMetodo = repositorioMetodo;
        this.servicioLogin = servicioLogin;
    }


    @Override
    public List<Metodo> buscarMetodos() {
        return repositorioMetodo.buscarMetodos();
    }

    @Override
    public void asociarHijo(Long hijoId, Long metodoId) {
        Hijo hijobuscardo= servicioLogin.busquedahijo(hijoId);
        Metodo metodobuscado= repositorioMetodo.buscarMetodoPorId(metodoId);
        hijobuscardo.setMetodo(metodobuscado);

    }

    @Override
    public Metodo buscarMetodoPorId(Long metodoId) {
        Metodo metodoBuscado = repositorioMetodo.traerMetodoPorId(metodoId);
        if (metodoBuscado == null) {
            throw new MetodoNoEncontradoPorID(metodoId);
        }
        return metodoBuscado;
    }

    @Override
    public Metodo buscarMetodoPorNombre(String nombreMetodo){
        return repositorioMetodo.buscarPorNombreDeMetodo(nombreMetodo);
    }
}
