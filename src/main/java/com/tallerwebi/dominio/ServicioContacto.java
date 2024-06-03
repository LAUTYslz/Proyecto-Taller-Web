package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioContacto {
    public Contacto guardar(Contacto contacto);
    public List<Contacto> traerContactos();

    void guardarContacto(Contacto contacto, String nombreMetodo, String nombreTipoContacto);

    void actualizarContacto(Contacto contactoExistente);

    void eliminarContacto(Contacto contactoExistente);

    //Contacto guardar(String nombre, String telefono, String mail, String direccion, String institucion, String nombreMetodo, String nombreTipo);
}
