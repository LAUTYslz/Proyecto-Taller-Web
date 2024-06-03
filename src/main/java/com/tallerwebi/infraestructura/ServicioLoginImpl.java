package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Hijo;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario consultarUsuario (String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());
        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }
        repositorioUsuario.guardar(usuario);
    }


    @Override
    public Usuario buscarUsuarioPorId(Long id ) throws UsuarioInexistente {
        Usuario usuarioEncontrado = repositorioUsuario.buscarPorId(id);
        if(usuarioEncontrado == null){
            throw new UsuarioInexistente();
        }
        return repositorioUsuario.buscarPorId(id);
    }

    @Override
    public void registrarHijo(Hijo hijo) {
        repositorioUsuario.guardarHijo(hijo);
    }

   /* @Override
   public void registrarConyuge(Long idUsuario, Usuario conyuge) throws UsuarioInexistente, UsuarioExistente {
        // Buscar al usuario actual en base a su ID
        Usuario usuario = repositorioUsuario.buscarPorId(idUsuario);

        // Verificar si el usuario existe
        if(usuario == null) {
            throw new UsuarioInexistente();
        }

        // Verificar si el cónyuge ya existe en la base de datos
        Usuario conyugeExistente = repositorioUsuario.buscarUsuario(conyuge.getEmail(), conyuge.getPassword());
        if(conyugeExistente == null) {
            // Si el cónyuge no existe, guardarlo en la base de datos
            repositorioUsuario.guardar(conyuge);
        } else {
            // Si el cónyuge ya existe, lanzar una excepción
            throw new UsuarioExistente();
        }

        // Asignar el cónyuge al usuario
        usuario.setConyuge(conyuge);

        // Actualizar el usuario en la base de datos para guardar la relación con el cónyuge
        repositorioUsuario.guardar(usuario);
        repositorioUsuario.guardar(conyuge);
    }*/

@Override
public void asociarConyuge(String userEmail, Usuario conyuge) {
        Usuario usuario = repositorioUsuario.findByEmail(userEmail);
        usuario.setConyuge(conyuge);
        conyuge.setConyuge(usuario);
       conyuge.setRol("ROL_CONYUGE");
        repositorioUsuario.guardar(usuario);
        repositorioUsuario.guardar(conyuge);
    }
}





