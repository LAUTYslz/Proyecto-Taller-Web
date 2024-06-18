package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EtapaInexistente;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.infraestructura.RepositorioMetodoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorMembresiaActivada {
    private final RepositorioMetodoImpl repositorioMetodoImpl;
    private ServicioMembresiaActivada servicioMembresiaActivada;
    private ServicioLogin servicioLogin;
    private ServicioAdmi servicioAdmi;
    private ServicioProfesional servicioProfesional;
    private ServicioMetodo servicioMetodo;
    private ServicioTipoProfesional servicioTipoProfesional;

    public ControladorMembresiaActivada(ServicioMembresiaActivada servicioMembresiaActivada, ServicioLogin servicioLogin, ServicioAdmi servicioAdmi, ServicioProfesional servicioProfesional, ServicioMetodo servicioMetodo, ServicioTipoProfesional servicioTipoProfesional, RepositorioMetodoImpl repositorioMetodoImpl) {
        this.servicioMembresiaActivada = servicioMembresiaActivada;
        this.servicioLogin = servicioLogin;
        this.servicioAdmi = servicioAdmi;
        this.servicioProfesional = servicioProfesional;
        this.servicioMetodo = servicioMetodo;
        this.servicioTipoProfesional = servicioTipoProfesional;
        this.repositorioMetodoImpl = repositorioMetodoImpl;
    }


    @GetMapping("/usuarioMembresia")
    public ModelAndView mostrarMembresiaActivada(HttpServletRequest request) throws UsuarioInexistente, UsuarioExistente {
        ModelAndView modelAndView = new ModelAndView();

        // Obtener el usuario actual
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        if (usuario != null) {
            // Obtener la membresía del usuario
            DatosMembresia membresia = usuario.getMembresia();

            // Obtener la lista de hijos del usuario
            List<Hijo> hijos = servicioLogin.buscarHijosPorId(usuario.getId());
            if (hijos == null) {
                // Si la lista de hijos es null, inicialízala como una lista vacía
                hijos = new ArrayList<>();
            }
            servicioLogin.actualizarUsuario(usuario);
            request.getSession().setAttribute("usuario", usuario);
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


    @GetMapping("/info")
    public ModelAndView verMetodos(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("info");
        // Obtener el usuario actual
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        if (usuario != null) {
            // Obtener la membresía del usuario
            DatosMembresia membresia = usuario.getMembresia();

            // Obtener la lista de hijos del usuario
            List<Hijo> hijos = servicioLogin.buscarHijosPorId(usuario.getId());
            if (hijos == null) {
                // Si la lista de hijos es null, inicialízala como una lista vacía
                hijos = new ArrayList<>();
            }

            List<Metodo> metodos = servicioMetodo.buscarMetodos();
            ;

            // Agregar el usuario, la membresía y la lista de hijos al modelo
            modelAndView.addObject("usuario", usuario);
            modelAndView.addObject("membresia", membresia);
            modelAndView.addObject("hijos", hijos);
            modelAndView.addObject("metodos", metodos);

        }
        return modelAndView;
    }

    @PostMapping("/asociar-metodo/{hijoId}")
    public ModelAndView seleccionarMetodoParaHijo(@PathVariable Long hijoId, @RequestParam Long metodo_id, HttpServletRequest request) throws UsuarioInexistente {
        // Obtener el usuario actual
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        // Buscar el hijo por su ID
        Hijo hijo = servicioLogin.busquedahijo(hijoId);

        if (usuario != null && hijo != null) {
            // Asociar el método al hijo
            servicioMetodo.asociarHijo(hijoId, metodo_id);

            // Actualizar el hijo en el servicio
            servicioLogin.actualizarHijo(hijo);

            // Actualizar el usuario para asegurarte de tener la membresía actualizada
            usuario = servicioLogin.buscarUsuarioPorId(usuario.getId());

            // Obtener la membresía actualizada del usuario
            DatosMembresia membresia = usuario.getMembresia();

            // Actualizar la sesión con los datos actualizados
            request.getSession().setAttribute("usuario", usuario);
            request.getSession().setAttribute("membresia", membresia);
            request.getSession().setAttribute("hijo", hijo);

            // Redirigir a la página de usuarioMembresia con el modelo actualizado
            return new ModelAndView("redirect:/usuarioMembresia");
        } else {
            // Manejar el caso en el que no se pueda obtener el usuario o el hijo
            return new ModelAndView("redirect:/error");
        }
    }
}

