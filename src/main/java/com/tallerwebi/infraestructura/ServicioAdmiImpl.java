package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EtapaInexistente;
import com.tallerwebi.dominio.excepcion.juegoInexistente;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Service("servicioAdmi")
@Transactional
public class ServicioAdmiImpl implements ServicioAdmi {
    private RepositorioUsuario repositorioUsuario;
    private RepositorioAdmi repositorioAdmi;

    public ServicioAdmiImpl(RepositorioUsuario repositorioUsuario, RepositorioAdmi repositorioAdmi) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioAdmi = repositorioAdmi;
    }

    @Override
    public void guardarEtapa(Etapa etapa) {
        repositorioAdmi.guardarEtapa(etapa);
    }


    @Override
    public List<Etapa> listaDeEtapas() {
        return repositorioAdmi.listaDeEtapas();
    }

    @Override
    public void guardarJuego(Juego juego) {
        repositorioAdmi.guardarJuego(juego);
    }

    @Override
    public List<Juego> listasDeJuegos() {
        return repositorioAdmi.listaDeJuegos();
    }

    @Override
    public Juego obtenerJuegoSesion(HttpServletRequest request) {
        return (Juego) request.getSession().getAttribute("juego");
    }

    @Override
    public Etapa obtenerEtapaSesion(HttpServletRequest request) {
        return (Etapa) request.getSession().getAttribute("etapa");
    }

    @Override
    public Etapa buscarEtapa(Long id) throws EtapaInexistente {
        Etapa etapaBuscada =repositorioAdmi.buscarEtapaPorId(id);
       if(etapaBuscada == null){
           throw new EtapaInexistente();
       }
       return etapaBuscada;
    }

    @Override
    public void actualizarEtapa(Etapa etapa) throws EtapaInexistente {
        Etapa buscarEtapa= repositorioAdmi.buscarEtapaPorId(etapa.getId());
        if(buscarEtapa == null){
            throw new EtapaInexistente();
        }
        buscarEtapa.setNombre(etapa.getNombre());
        buscarEtapa.setDesde(etapa.getDesde());
        buscarEtapa.setHasta(etapa.getHasta());
        repositorioAdmi.actualizarEtapas(buscarEtapa);
}

    @Override
    public List<Juego> listasDeJuegosPorEtapa(Long id) {
       return  repositorioAdmi.listarjuegosEtapas(id);

    }

    @Override
    public void eliminarEtapa(Etapa etapa) {
        repositorioAdmi.eliminarEtapas(etapa);
    }

    @Override
    public Juego buscarJuegoPorId(Long id) throws juegoInexistente {
        Juego juegoBuscado =repositorioAdmi.buscarJuegoPorId(id);
        if(juegoBuscado == null){
            throw new juegoInexistente();
        }
        return juegoBuscado;
    }


    @Override
    public void actualizarJuego(Juego juego) throws juegoInexistente {
        Juego buscarJuego= repositorioAdmi.buscarJuegoPorId(juego.getId());
        if(buscarJuego == null){
            throw new juegoInexistente();
        }
        buscarJuego.setNombre(juego.getNombre());
        buscarJuego.setDescripcion(juego.getDescripcion());
        repositorioAdmi.actualizarJuegos(buscarJuego);
    }

    @Override
    public void eliminarJuego(Juego juego) {
        repositorioAdmi.eliminarJuegos(juego);
    }

    @Override
    public Caja obtenerCaja() {
        return repositorioAdmi.obtenerCajaDeProyecto();
    }
}
