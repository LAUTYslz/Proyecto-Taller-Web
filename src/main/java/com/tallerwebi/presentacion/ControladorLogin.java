package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Etapa;
import com.tallerwebi.dominio.Hijo;

import com.tallerwebi.dominio.ServicioLogin;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;


import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller

public class ControladorLogin {

    private final ServicioLogin servicioLogin;


    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin ) {
        this.servicioLogin = servicioLogin;

    }


    @RequestMapping("/login")
    public ModelAndView irALogin() {

        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("login", modelo);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
        if (usuarioBuscado != null) {
            request.getSession().setAttribute("usuario", usuarioBuscado); // Guardar el objeto Usuario completo en la sesión
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
            if(usuarioBuscado.getRol().equals("ADMIN")) {
                return new ModelAndView("redirect:/administrador");
            }
            return new ModelAndView("redirect:/bienvenido");
        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("redirect:/login");
    }


    @RequestMapping(path = "/registrarse", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario) {
        ModelMap model = new ModelMap();
        try {
            servicioLogin.registrar(usuario);
        } catch (UsuarioExistente e) {
            model.put("error", "El usuario ya existe");
            return new ModelAndView("nuevo-usuario", model);
        } catch (Exception e) {
            model.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("nuevo-usuario", model);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/nuevo-usuario")
    public ModelAndView nuevoUsuario() {
        ModelMap model = new ModelMap();
        model.put("usuario", new Usuario());
        return new ModelAndView("nuevo-usuario", model);
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome() {

        return new ModelAndView("home");
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/login");
    }







    @RequestMapping(path = "/bienvenido")
    public ModelAndView irABienvenido(HttpServletRequest request) {
        ModelAndView modelo = new ModelAndView();
        Usuario usuario = servicioLogin.obtenerUsuarioActual( request);
        modelo.addObject("usuario", usuario);
        if (usuario == null) {
            // Manejar el caso en el que el usuario es nulo, por ejemplo, redirigir a la página de inicio de sesión
            return new ModelAndView("redirect:/login");
        }

        ModelMap model = new ModelMap();

        model.put("usuario", usuario);
        return new ModelAndView("bienvenido", model);
    }


    @GetMapping("/bienvenido")
    public ModelAndView mostrarBienvenido(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        // Obtener el usuario actual
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        // Obtener la lista de hijos del usuario
        List<Hijo> hijos = servicioLogin.buscarHijosPorId(usuario.getId());

        // Agregar el usuario y la lista de hijos al modelo
        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("hijos", hijos);

        // Establecer la vista
        modelAndView.setViewName("bienvenido");

        return modelAndView;
    }


    @RequestMapping(path = "/modificarUsuario")
    public ModelAndView irAModificarDatos() {
        return new ModelAndView("modificarUsuario");
    }

    @RequestMapping(path = "/guardar-usuario", method = RequestMethod.POST)
    public ModelAndView irAModificarUsuario(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        try {
            // me tare el atributo de la id???

            Long id = (Long) request.getSession().getAttribute("id");
            // Utiliza el ID para buscar el usuario

            Usuario usuario = servicioLogin.buscarUsuarioPorId(id);
            model.put("usuario", usuario);
        } catch (UsuarioInexistente e) {
            model.put("error", "El usuario es inexistente");
            return new ModelAndView("bienvenido", model);
        } catch (Exception e) {
            model.put("error", "Error al buscar el  usuario");
            return new ModelAndView("bienvenido", model);
        }

        return new ModelAndView("modificarUsuario", model);
    }








    //creo un hijo
    @RequestMapping(path = "/nuevoHijo")
    public ModelAndView irAaLTA() {
        ModelMap modelo = new ModelMap();
        modelo.put("hijo", new Hijo());
        return new ModelAndView("nuevoHijo", modelo);

    }

    //creo un hijo
    @RequestMapping(path = "/guardar-hijo")
    public ModelAndView irAGuardar() {

        return new ModelAndView("guardar-hijo");

    }
    @RequestMapping(path = "/guardar-hijo", method = RequestMethod.POST)
    public ModelAndView guardarHijo(@ModelAttribute("hijo") Hijo hijo, HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        try {
            Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

            // Asociar el hijo con el usuario
            hijo.setUsuario(usuario);
            servicioLogin.registrarHijo(hijo);
            Etapa etapaEncontrada = servicioLogin.asignarEtapa(hijo);

            if (etapaEncontrada == null) {
                modelo.put("error", "No se encontró una etapa para la edad proporcionada");
                return new ModelAndView("error", modelo); // Manejar el caso de error
            }

            hijo.setEtapa(etapaEncontrada);
            modelo.addAttribute("hijo", hijo);
            modelo.put("usuario", usuario);
        } catch (Exception e) {
            modelo.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("error", modelo); // Manejar cualquier otro error
        }
        return new ModelAndView("guardar-hijo", modelo);
    }

    // Mostrar formulario para agregar cónyuge
    @GetMapping("/nuevoConyuge")
    public String mostrarFormularioAgregarConyuge(HttpServletRequest request, Model model) {
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);
        model.addAttribute("usuario", usuario);
        model.addAttribute("conyuge", new Usuario());
        return "nuevoConyuge";
    }



    @PostMapping("/guardar-conyuge")
    public ModelAndView registrarConyuge(HttpServletRequest request, @ModelAttribute("conyuge") Usuario conyuge) {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelo = new ModelMap();

        Usuario usuarioActual = servicioLogin.obtenerUsuarioActual(request);
        servicioLogin.asociarConyuge(usuarioActual.getEmail(), conyuge);
        conyuge.setRol("ROL_CONYUGE");
        modelo.put("usuario",conyuge);
        modelAndView.addObject("usuario", conyuge);
        return new ModelAndView("detalles-conyuge",modelo); // Redirigir a la página de perfil
       /* } catch (UsuarioInexistente e) {
            // Manejar el caso de que el cónyuge ya exista
            modelAndView.setViewName("error");*/ // Puedes redirigir a una página de error o mostrar un mensaje de error al usuario
       /* } catch (UsuarioExistente e) {
            // Manejar el caso de que el usuario no exista
            modelAndView.setViewName("error"); */// Puedes redirigir a una página de error o mostrar un mensaje de error al usuario


    }

    @GetMapping("/detalles-conyuge")
    public ModelAndView mostrarDetallesUsuario(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        // Obtener el cónyuge del usuario
        Usuario conyuge = usuario.getConyuge();

        // Agregar el usuario y el cónyuge al modelo
        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("conyuge", conyuge);

        modelAndView.setViewName("detalles-conyuge");
        return modelAndView;
    }

    @PostMapping("/eliminar-hijo/{hijoId}")
    public String eliminarHijo(@PathVariable("hijoId") Long hijoId) {
        servicioLogin.eliminarHijo(hijoId);
        return "redirect:/bienvenido";
    }
}