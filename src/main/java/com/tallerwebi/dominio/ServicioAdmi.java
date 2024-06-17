package com.tallerwebi.dominio;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ServicioAdmi {

    void guardarEtapa(Etapa etapa);

    List<Etapa> listaDeEtapas();

    void guardarJuego(Juego juego);

    List<Juego> listasDeJuegos();

  

    Juego obtenerJuegoSesion(HttpServletRequest request);

    Etapa obtenerEtapaSesion(HttpServletRequest request);
}
