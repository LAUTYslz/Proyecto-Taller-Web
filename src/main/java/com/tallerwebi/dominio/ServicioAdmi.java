package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EtapaInexistente;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ServicioAdmi {

    void guardarEtapa(Etapa etapa);

    List<Etapa> listaDeEtapas();

    void guardarJuego(Juego juego);

    List<Juego> listasDeJuegos();

  

    Juego obtenerJuegoSesion(HttpServletRequest request);

    Etapa obtenerEtapaSesion(HttpServletRequest request);

    Etapa buscarEtapa(Long id) throws EtapaInexistente;

    void actualizarEtapa(Etapa etapa) throws EtapaInexistente;

    List<Juego> listasDeJuegosPorEtapa(Long id);
}
