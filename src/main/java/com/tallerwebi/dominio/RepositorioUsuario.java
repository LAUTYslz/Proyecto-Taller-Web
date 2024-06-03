package com.tallerwebi.dominio;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);

    Usuario buscarPorId(Long id);

    void guardarHijo(Hijo hijo);

    void actualizar(Usuario usuario);

    Usuario findByEmail(String userEmail);



    /*void guardarHijo(Usuario hijo);*/
}

