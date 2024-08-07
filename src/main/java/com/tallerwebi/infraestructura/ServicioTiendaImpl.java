package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioTienda;
import com.tallerwebi.dominio.ServicioTienda;
import com.tallerwebi.dominio.Tienda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioTienda")
@Transactional
public class ServicioTiendaImpl implements ServicioTienda {

    private final RepositorioTienda repositorioTienda;

    @Autowired
    public ServicioTiendaImpl(RepositorioTienda repositorioTienda) {
        this.repositorioTienda = repositorioTienda;
    }

    @Override
    public List<Tienda> obtenerListadoDeTiendas() {
        return this.repositorioTienda.obtenerListadoDeTiendas();
    }

    @Override
    public Tienda obtenerTiendaPorId(Long id) {
        return this.repositorioTienda.obtenerTiendaPorId(id);
    }

    @Override
    public void guardarTienda(Tienda tienda) {
        this.repositorioTienda.guardarTienda(tienda);
    }

    @Override
    public void eliminarTienda(Long id) {
        this.repositorioTienda.eliminarTienda(id);
    }

    @Override
    public void actualizarTienda(Tienda tienda) {
        this.repositorioTienda.actualizarTienda(tienda);
    }
}
