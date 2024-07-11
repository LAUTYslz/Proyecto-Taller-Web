package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioVacunacion {

    void guardar(Vacunacion vacunacion);

    List<Vacunacion> obtenerVacunacionesPorHijo(Long hijoId);

    void eliminar(Vacunacion vacunacion);
}
