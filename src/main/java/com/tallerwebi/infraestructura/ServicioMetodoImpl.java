package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Metodo;
import com.tallerwebi.dominio.RepositorioMetodo;
import com.tallerwebi.dominio.ServicioMetodo;
import com.tallerwebi.dominio.excepcion.MetodoNoEncontradoPorID;
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
