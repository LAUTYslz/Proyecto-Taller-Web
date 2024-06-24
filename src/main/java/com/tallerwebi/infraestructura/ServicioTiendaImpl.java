package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioTienda;
import com.tallerwebi.dominio.ServicioTienda;
import com.tallerwebi.dominio.Tienda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioTienda")
@Transactional
public class ServicioTiendaImpl implements ServicioTienda {

    private final RepositorioTienda repositorioTienda;

    @Autowired
    public ServicioTiendaImpl(RepositorioTienda repositorioTienda) {
        this.repositorioTienda = repositorioTienda;
    }

    @Override
    public Tienda obtenerListadoDeTiendas() {
        return null;
    }

    @Override
    public String obtenerInfoTienda(Long id) {
        return "";
    }

    @Override
    public Boolean guardarTienda(Tienda tienda) {
        return null;
    }

    @Override
    public Boolean eliminarTienda(Long id) {
        return null;
    }
}
