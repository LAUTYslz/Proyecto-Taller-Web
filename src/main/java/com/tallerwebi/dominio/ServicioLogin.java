package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws UsuarioExistente;
    Usuario buscarUsuarioPorId(Long id)throws UsuarioInexistente;

    void registrarHijo( Hijo hijo1);

    void asociarConyuge(String userEmail, Usuario conyuge);

    /* void registrarConyuge(Long id, Usuario conyuge) throws UsuarioExistente, UsuarioInexistente;*/

    /* void registrarHijo(Usuario hijo, Usuario usuario)throws UsuarioInexistente ;*/
}
