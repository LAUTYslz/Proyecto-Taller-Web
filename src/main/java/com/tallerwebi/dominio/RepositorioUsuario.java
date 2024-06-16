package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);

    Usuario buscarPorId(Long id);

    void guardarHijo(Hijo hijo);

    void actualizar(Usuario usuario);

    Usuario findByEmail(String userEmail);



    List<Hijo> buscarHijosPorId(Long usuarioid);





    void borrarHijo(Long hijoId);

    void actualizarHijo(Hijo hijo);



    /*void guardarHijo(Usuario hijo);*/
}

