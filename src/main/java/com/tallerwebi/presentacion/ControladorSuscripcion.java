package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.DatosMembresia;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioMembresia;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorSuscripcion {

    private ServicioMembresia servicioMembresia;

    @Autowired
    public ControladorSuscripcion(ServicioMembresia servicioMembresia){
        this.servicioMembresia = servicioMembresia;
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
    public ModelAndView procesarDatosDeMembresiaPaga(@ModelAttribute("datosMembresia") DatosMembresia datosMembresia){
        ModelMap model = new ModelMap();
        try {
            servicioMembresia.darDeAltaMembresia(datosMembresia);
            model.put("datosMembresia", datosMembresia);
            return new ModelAndView("confirmacionMembresia", model);
        } catch (MembresiaExistente ex){
            model.put("error", "Tu usuario ya cuenta con una membresía paga");
        } catch (TarjetaInvalida ex){
            model.put("error", "El número de tarjeta es inválido");
        } catch (CodigoInvalido e){
            model.put("error", "El código de seguridad es inválido");
        } catch (TarjetaVencida e){
            model.put("error", "La fecha de vencimiento de la tarjeta es inválida");
        } catch (UsuarioInexistente e){
            model.put("error", "El email que estás ingresando no pertenece a ningún usuario");
        }

        return new ModelAndView("membresiaPaga", model);
    }

}