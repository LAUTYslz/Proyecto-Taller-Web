package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioProfesional {
    Profesional buscarProfesional(String email, String password);
    void guardar(Profesional profesional);
    Profesional buscar(String email);
    void modificar(Profesional profesional);
    void eliminar(Profesional profesional);

    List<Profesional> traerProfesionales();

    List<Profesional> traerProfesionalesPorMetodo(String nombreMetodo);

    List<Profesional> traerProfesionalesPorTipo(String nombreTipo);

    List<Profesional> traerProfesionalesPorTipoYMetodo(String nombreTipo, String nombreMetodo);
}
