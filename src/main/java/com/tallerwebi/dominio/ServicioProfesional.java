package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioProfesional {
    public Profesional guardar(Profesional contacto);
    public List<Profesional> traerProfesionales();

    void guardarProfesional(Profesional profesional, String nombreMetodo, String nombreTipoContacto);

    void actualizarProfesional(Profesional profesionalExistente, String nombreMetodo, String nombreTipoContacto);
    void actualizar(Profesional profesional);
    void eliminarProfesional(Profesional profesionalExistente);

    List<Profesional> traerProfesionalesPorMetodo(String nombreMetodo);

    List<Profesional> traerProfesionalesPorTipo(String nombreTipo);

    List<Profesional> traerProfesionalesPorTipoYMetodo(String nombreTipo, String nombreMetodo);

    List<Metodo> traerTodosLosMetodos();

    List<TipoProfesional> traerTodosLosTipos();

    Profesional obtenerPorId(Long id);

    List<Profesional> traerProfesionalesSinTienda();

    Integer calcularMontoACobrar(String mail);

    //Contacto guardar(String nombre, String telefono, String mail, String direccion, String institucion, String nombreMetodo, String nombreTipo);
}
