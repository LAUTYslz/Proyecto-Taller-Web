package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioContacto {
    public Contacto guardar(Contacto contacto);
    public List<Contacto> traerContactos();

    void guardarContacto(Contacto contacto, String nombreMetodo, String nombreTipoContacto);

    void actualizarContacto(Contacto contactoExistente);

    void eliminarContacto(Contacto contactoExistente);

    List<Contacto> traerContactosPorMetodo(String nombreMetodo);

    List<Contacto> traerContactosPorTipo(String nombreTipo);

    List<Contacto> traerContactosPorTipoYMetodo(String nombreTipo, String nombreMetodo);

    List<Metodo> traerTodosLosMetodos();

    List<TipoContacto> traerTodosLosTipo();

    //Contacto guardar(String nombre, String telefono, String mail, String direccion, String institucion, String nombreMetodo, String nombreTipo);
}
