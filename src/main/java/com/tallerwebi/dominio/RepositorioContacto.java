package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioContacto {
    Contacto buscarContacto(String email, String password);
    void guardar(Contacto contacto);
    Contacto buscar(String email);
    void modificar(Contacto contacto);

    List<Contacto> traerContactos();

    List<Contacto> traerContactosPorMetodo(String nombreMetodo);

    List<Contacto> traerContactosPorTipo(String nombreTipo);
}
