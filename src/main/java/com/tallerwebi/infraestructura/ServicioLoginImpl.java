package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;
    private RepositorioAdmi repositorioAdmi;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario, RepositorioAdmi repositorioAdmi) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioAdmi = repositorioAdmi;
    }

    @Override
    public Usuario consultarUsuario(String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());
        if (usuarioEncontrado != null) {
            throw new UsuarioExistente();
        }
        repositorioUsuario.guardar(usuario);
    }


    @Override
    public Usuario buscarUsuarioPorId(Long id) throws UsuarioInexistente {
        Usuario usuarioEncontrado = repositorioUsuario.buscarPorId(id);
        if (usuarioEncontrado == null) {
            throw new UsuarioInexistente();
        }
        return usuarioEncontrado;
    }

    @Override
    public void registrarHijo(Hijo hijo) {
        asignarEtapa(hijo);
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

    @Override
    public void setUltimoHijoAgregado(HttpServletRequest request, Hijo hijo) {

        request.getSession().setAttribute("hijo", hijo);
    }


    @Override
    public Hijo obtenerUltimoHijoAgregado(HttpServletRequest request) {
        return (Hijo) request.getSession().getAttribute("hijo");
    }

    @Override
    public Usuario obtenerUsuarioActual(HttpServletRequest request) {
        return (Usuario) request.getSession().getAttribute("usuario");

    }

    @Override
    public List<Hijo> buscarHijosPorId(Long usuarioid) {
        return repositorioUsuario.buscarHijosPorId(usuarioid);
    }

    @Override
    public void eliminarHijo(Long hijoId) {
        repositorioUsuario.borrarHijo(hijoId);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) throws UsuarioInexistente {
        Usuario buscar= repositorioUsuario.buscarPorId(usuario.getId());
        if (buscar == null) {
            throw new UsuarioInexistente();
        }
        buscar.setEmail(usuario.getEmail());
        buscar.setPassword(usuario.getPassword());
        repositorioUsuario.actualizar(buscar);
    }

    public void asignarEtapa(Hijo hijo) {
        List<Etapa> listaEtapa = repositorioAdmi.listaDeEtapas();
        Integer edad = hijo.getEdad();
        Etapa etapaEncontrada = null;

        for (Etapa etapa : listaEtapa) {
            if (edad >= etapa.getDesde() && edad <= etapa.getHasta()) {
                etapaEncontrada = etapa;
                break; // Se encontró la etapa, no es necesario continuar iterando
            }
        }

        if (etapaEncontrada == null) {
            // Manejar el caso en el que ninguna etapa sea encontrada
            throw new RuntimeException("No se encontró una etapa para la edad proporcionada");
        }

        // Asignar la etapa encontrada al hijo
        hijo.setEtapa(etapaEncontrada);
    }
}



