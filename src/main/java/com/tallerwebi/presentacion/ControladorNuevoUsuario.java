package com.tallerwebi.presentacion;

import com.tallerwebi.Dto.DatosRegistro;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioNuevoUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorNuevoUsuario {

    ServicioNuevoUsuario servicioNuevoUsuario;

    @Autowired
    public ControladorNuevoUsuario(ServicioNuevoUsuario servicioNuevoUsuario) {
        this.servicioNuevoUsuario = servicioNuevoUsuario;
    }

    @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario() {
        ModelMap model = new ModelMap();
        model.put("usuario", new Usuario());
        return new ModelAndView("nuevo-usuario", model);
    }

    public ModelAndView registrar(DatosRegistro datos) {
        ModelMap model = new ModelMap();
        if(!datos.getPassword().equals(datos.getRepeticion())) {
            return registroFallido(model, "Las password no coinciden");
        }
        if (datos.getMail().isEmpty()){
            return registroFallido(model, "El email no puede estar vacio");
        }
        if(datos.getNombre().isEmpty()){
            return registroFallido(model, "El nombre no puede estar vacio");
        }
        if (datos.getDireccion().isEmpty()){
            return registroFallido(model, "La direccion no puede estar vacia");
        }
        try {
            servicioNuevoUsuario.registrar(datos.getMail(), datos.getPassword(), datos.getNombre(), datos.getDireccion());
        }catch (PasswordLongitudIncorrecta exception){
            return  registroFallido(model, "Password con longitud incorrecta");
        }
        return registroExitoso();
    }

    private  ModelAndView registroExitoso() {
        return new ModelAndView("login");
    }

    private ModelAndView registroFallido(ModelMap model, String mensaje){
        model.put("error",mensaje);
        return new ModelAndView("nuevo-usuario", model);
    }




}
