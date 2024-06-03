package com.tallerwebi.Infraestructura;

import com.tallerwebi.dominio.ServicioNuevoUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("ServicioNuevoUsuario")
@Transactional
public class ServicioNuevoUsuarioImpl implements ServicioNuevoUsuario {

    @Override
    public Usuario registrar(String email, String password, String nombre, String direccion) {
        if(password.length() < 6) {
            throw new PasswordLongitudIncorrecta();
        }
        Usuario usuario = new Usuario();
        return usuario;
    }
}
