package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorMetodos {

    private final ServicioLogin servicioLogin;

    @Autowired
    public ControladorMetodos(ServicioLogin servicioLogin, ServicioMetodo servicioMetodo) {
        this.servicioLogin = servicioLogin;
    }

    @GetMapping("/verHijoEInfoMetodoAsociado")
    public String seleccionarHijo(Model model, HttpServletRequest request) {
        Usuario usuarioActual = servicioLogin.obtenerUsuarioActual(request);
        if (usuarioActual != null) {
            List<Hijo> hijos = servicioLogin.buscarHijosPorId(usuarioActual.getId());
            model.addAttribute("hijos", hijos);
        }
        return "verHijoEInfoMetodoAsociado";
    }

    @PostMapping("/verMetodo")
    public String verMetodo(@RequestParam("hijoId") Long hijoId, Model model) {
        Hijo hijo = servicioLogin.busquedahijo(hijoId);
        if (hijo.getMetodo() != null) {
            Metodo metodo = hijo.getMetodo();
            model.addAttribute("metodo", metodo);
            return metodo.getNombre().toLowerCase();
        } else {
            model.addAttribute("error", "Este hijo no tiene un método de crianza asociado.");
            return "verHijoEInfoMetodoAsociado";
        }
    }
}