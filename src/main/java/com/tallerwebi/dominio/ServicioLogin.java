package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NoposeeEtapa;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.dominio.excepcion.UsuarioNoPoseeMembresiaActivada;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws UsuarioExistente;
    Usuario buscarUsuarioPorId(Long id)throws UsuarioInexistente;

    void registrarHijo( Hijo hijo1) throws UsuarioNoPoseeMembresiaActivada, NoposeeEtapa;

    void registrarConyuge(Long idUsuario, Usuario conyuge) throws UsuarioInexistente, UsuarioExistente;

    void asociarConyuge(String userEmail, Usuario conyuge);

    void setUltimoHijoAgregado(HttpServletRequest request, Hijo hijo);


    Hijo obtenerUltimoHijoAgregado(HttpServletRequest request);

    Usuario obtenerUsuarioActual(HttpServletRequest request);



    List<Hijo> buscarHijosPorId(Long usuarioid);

    void eliminarHijo(Long hijoId);

    void actualizarUsuario(Usuario buscado) throws UsuarioExistente, UsuarioInexistente;



    Hijo busquedahijo(Long hijoid);

    void actualizarHijo(Hijo buscarHijo);

    void registrarUsuarioProfesional(Usuario usuarioProf);

    /* Etapa asignarEtapa(Hijo hijo);*/

    /* void registrarConyuge(Long id, Usuario conyuge) throws UsuarioExistente, UsuarioInexistente;*/

    /* void registrarHijo(Usuario hijo, Usuario usuario)throws UsuarioInexistente ;*/
}
