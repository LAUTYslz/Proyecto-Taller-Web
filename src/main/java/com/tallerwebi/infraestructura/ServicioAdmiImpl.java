package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
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

}
