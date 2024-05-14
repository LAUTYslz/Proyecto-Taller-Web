package com.tallerwebi.dominio;

public interface ServicioNuevoUsuario {
    Usuario registrar(String email, String password, String nombre, String direccion);
}
