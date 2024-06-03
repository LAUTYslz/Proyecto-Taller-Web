package com.tallerwebi.presentacion;


//import com.tallerwebi.DTo.DatosRegistro;
//import com.tallerwebi.dominio.ServicioUsuario;
//import com.tallerwebi.dominio.excepcion.passwordLongitudIncorrecta;
import com.tallerwebi.Dto.DatosRegistro;
import com.tallerwebi.dominio.ServicioNuevoUsuario;
import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ControladorNuevoUsuarioTest {
    /*
     * 1.Para registrar un usario necesito email, pass, nombre y direccion
     * 2.El registro falla si pass y repeticion no coinciden
     * 3.Que la pass debe tener mas 6 caracteres
     *
     * */

    ServicioNuevoUsuario servicioNuevoUsuario = mock(ServicioNuevoUsuario.class);
    ControladorNuevoUsuario controladorNuevoUsuario = new ControladorNuevoUsuario(servicioNuevoUsuario);
    @Test
    public void paraRegistrarUnUsuarioNecesitoEmailPassNombreYDireccion(){
        givenNoExisteUsuario();
        ModelAndView mav = whenRegistroUsuario("lau.gmail.com", "123", "123", "Lautaro Salazar", "San Matias 1278");
        thenRegistroExitoso(mav);
    }

    private ModelAndView whenRegistroUsuario(String email, String pass, String repeticion, String nombre, String direccion) {
        DatosRegistro datosRegitro = new DatosRegistro(email,pass,repeticion,nombre,direccion);
        ModelAndView mav = controladorNuevoUsuario.registrar(datosRegitro);
        return mav;
    }
    private void thenRegistroExitoso(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("login"));
    }
    private void givenNoExisteUsuario() {
    }

    @Test
    public void elRegistroFallaSiPasswordYRepeticionNocoinciden(){
        givenNoExisteUsuario();
        ModelAndView mav = whenRegistroUsuario("lau.gmail.com", "123", "1234", "Lautaro Salazar", "San Matias 1278");
        String mensaje = "Las password no coinciden";
        thenRegistroFalla(mav, mensaje);
    }
    private void thenRegistroFalla(ModelAndView mav, String mensaje) {
        assertThat(mav.getViewName(),equalToIgnoringCase("nuevo-usuario"));
        assertThat(mav.getModel().get("error").toString(),equalToIgnoringCase(mensaje));
    }

    @Test
    public void elRegistroFallaSiNoExisteEmail(){
        givenNoExisteUsuario();
        ModelAndView mav = whenRegistroUsuario("", "1234", "1234", "Lautaro Salazar", "San Matias 1278");
        String mensaje = "El email no puede estar vacio";
        thenRegistroFalla(mav,mensaje);
    }

    @Test
    public void siElPasswordTieneMenosDeSeisCaracteresElRegistroFalla(){
        givenNoExisteUsuario();
        doThrow(PasswordLongitudIncorrecta.class)
                .when(servicioNuevoUsuario)
                .registrar("lau.gmail.com", "1234","Lautaro Salazar", "San Matias 1278");
        ModelAndView mav = whenRegistroUsuario("lau.gmail.com", "1234", "1234", "Lautaro Salazar", "San Matias 1278");
        String mensaje = "Password con longitud incorrecta";
        thenRegistroFalla(mav,mensaje);
    }

    @Test
    public void elRegistroFallaSiNoExisteNombre(){
        givenNoExisteUsuario();
        ModelAndView mav = whenRegistroUsuario("lau.gmail.com", "1234", "1234", "", "San Matias 1278");
        String mensaje = "El nombre no puede estar vacio";
        thenRegistroFalla(mav,mensaje);
    }

    @Test
    public void elRegistroFallaSiNoExisteDireccion(){
        givenNoExisteUsuario();
        ModelAndView mav = whenRegistroUsuario("lau.gmail.com", "1234", "1234", "Lautaro Salazar", "");
        String mensaje = "La direccion no puede estar vacia";
        thenRegistroFalla(mav,mensaje);
    }


}
