package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.DatosMembresia;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioMembresia;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.CodigoInvalido;
import com.tallerwebi.dominio.excepcion.MembresiaExistente;
import com.tallerwebi.dominio.excepcion.TarjetaInvalida;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorSuscripcion {

    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    private ServicioMembresia servicioMembresia;
    private final ServicioLogin servicioLogin;


    public ControladorSuscripcion(ServicioMembresia servicioMembresia, ServicioLogin servicioLogin, RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        this.servicioMembresia = servicioMembresia;
        this.servicioLogin = servicioLogin;
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
    }

    @RequestMapping("/suscripcion")
    public ModelAndView irASuscripcion() {
        return new ModelAndView("suscripcion");
    }

    @RequestMapping("/membresiaPaga")
    public ModelAndView irAMembresiaPaga() {
        ModelMap model = new ModelMap();
        model.put("datosMembresia", new DatosMembresia());
        return new ModelAndView("membresiaPaga", model);
    }

    @RequestMapping(path = "/procesarMembresiaPaga", method = RequestMethod.POST)
    public ModelAndView procesarDatosDeMembresiaPaga(@ModelAttribute("datosMembresia") DatosMembresia datosMembresia , HttpServletRequest request){
        ModelMap model = new ModelMap();
       Usuario usuario= servicioLogin.obtenerUsuarioActual(request);
        try {
            servicioMembresia.darDeAltaMembresia(datosMembresia,usuario);
            model.put("datosMembresia", datosMembresia);
            model.put("usuario", usuario);
            return new ModelAndView("confirmacionMembresia", model);
        } catch (MembresiaExistente ex){
            model.put("error", "Tu usuario ya cuenta con una membresía paga");
        } catch (TarjetaInvalida ex){
            model.put("error", "El número de tarjeta es inválido");
        } catch (CodigoInvalido e){
            model.put("error", "El código de seguridad es inválido");
        } catch (UsuarioInexistente e) {
            throw new RuntimeException(e);
        }

        return new ModelAndView("login", model);
    }

}