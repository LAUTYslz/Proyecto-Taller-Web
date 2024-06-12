package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioAdmi {



    List<Etapa> listaDeEtapas();

    void guardarEtapa(Etapa etapa);

    void guardarJuego(Juego juego);

    List<Juego> listaDeJuegos();

    Etapa buscarEtapaPorId(Long id);



    void actualizarEtapas(Etapa etapa);
}
