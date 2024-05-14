package com.tallerwebi.Dominio;

import com.tallerwebi.dominio.ServicioNuevoUsuario;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tallerwebi.infraestructura.ServicioNuevoUsuarioImpl;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import org.junit.jupiter.api.Test;


public class ServicioNuevoUsuarioTest{

    ServicioNuevoUsuario servicioNuevoUsuario = new ServicioNuevoUsuarioImpl() {
    };

    @Test
    public void siElUsuarioTieneEmailPassNombreYDireccionElRegistroEsExitoso() {
        givenNoExisteUsuario();
        Usuario usuario = whenRegistroUsuario("lau@prueba.com", "123456", "Lautaro Salazar", "San Matias 1278");

        thenElRegistroEsExitoso(usuario);
    }

    private void givenNoExisteUsuario() {
    }

    private Usuario whenRegistroUsuario(String email, String password, String nombre, String direccion) {
        return servicioNuevoUsuario.registrar(email,password,nombre,direccion);
    }

    private void thenElRegistroEsExitoso(Usuario usuario) {
        assertThat(usuario, notNullValue());
    }


    @Test
    public void siLaPasswordTieneMenosDe6CaracteresElRegistroFalla() {
        givenNoExisteUsuario();
        //Usuario usuario = whenRegistroUsuario("lau@prueba.com", "123");
        assertThrows(PasswordLongitudIncorrecta.class,
                ()->whenRegistroUsuario("lau@prueba.com", "123", "Lautaro Salazar", "San Matias 1278"));
        //thenElRegistroFalla(usuario);
    }

    private void thenElRegistroFalla(Usuario usuario) {
        assertThat(usuario, nullValue());
    }


}
