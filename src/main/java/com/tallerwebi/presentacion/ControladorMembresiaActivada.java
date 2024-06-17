package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorMembresiaActivada {
    private ServicioMembresiaActivada servicioMembresiaActivada;
    private ServicioLogin servicioLogin;
    private ServicioAdmi servicioAdmi;
    private ServicioProfesional servicioProfesional;
    private ServicioMetodo servicioMetodo;
    private ServicioTipoProfesional servicioTipoProfesional;

    public ControladorMembresiaActivada(ServicioMembresiaActivada servicioMembresiaActivada, ServicioLogin servicioLogin, ServicioAdmi servicioAdmi, ServicioProfesional servicioProfesional, ServicioMetodo servicioMetodo, ServicioTipoProfesional servicioTipoProfesional) {
        this.servicioMembresiaActivada = servicioMembresiaActivada;
        this.servicioLogin = servicioLogin;
        this.servicioAdmi = servicioAdmi;
        this.servicioProfesional = servicioProfesional;
        this.servicioMetodo = servicioMetodo;
        this.servicioTipoProfesional = servicioTipoProfesional;
    }


    @GetMapping("/usuarioMembresia")
    public ModelAndView mostrarMembresiaActivada(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        // Obtener el usuario actual
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        if (usuario != null) {
            // Obtener la membresía del usuario
            DatosMembresia membresia = usuario.getMembresia();

            // Obtener la lista de hijos del usuario
            List<Hijo> hijos = servicioLogin.buscarHijosPorId(usuario.getId());

            // Agregar el usuario, la membresía y la lista de hijos al modelo
            modelAndView.addObject("usuario", usuario);
            modelAndView.addObject("membresia", membresia);
            modelAndView.addObject("hijos", hijos);

            // Establecer la vista como "bienvenido"
            modelAndView.setViewName("usuarioMembresia");
        } else {
            // Manejar el caso en el que no se pudo obtener el usuario
            modelAndView.setViewName("redirect:/error"); // Redirigir a una página de error si es necesario
        }

        return modelAndView;
    }
}
