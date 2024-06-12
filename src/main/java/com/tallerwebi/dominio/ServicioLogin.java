package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws UsuarioExistente;
    Usuario buscarUsuarioPorId(Long id)throws UsuarioInexistente;

    void registrarHijo( Hijo hijo1);

    void asociarConyuge(String userEmail, Usuario conyuge);

    void setUltimoHijoAgregado(HttpServletRequest request, Hijo hijo);


    Hijo obtenerUltimoHijoAgregado(HttpServletRequest request);

    Usuario obtenerUsuarioActual(HttpServletRequest request);



    List<Hijo> buscarHijosPorId(Long usuarioid);

    void eliminarHijo(Long hijoId);

    void actualizarUsuario(Usuario buscado) throws UsuarioExistente, UsuarioInexistente;

    /* Etapa asignarEtapa(Hijo hijo);*/

    /* void registrarConyuge(Long id, Usuario conyuge) throws UsuarioExistente, UsuarioInexistente;*/

    /* void registrarHijo(Usuario hijo, Usuario usuario)throws UsuarioInexistente ;*/
}
