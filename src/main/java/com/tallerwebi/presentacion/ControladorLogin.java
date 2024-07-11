package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;

import com.tallerwebi.dominio.excepcion.*;


import com.tallerwebi.infraestructura.ServicioMembresiaImpl;
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

    private final ServicioMembresia servicioMembresia;

    @Autowired

    public ControladorLogin(ServicioLogin servicioLogin, ServicioMembresia servicioMembresia) {
        this.servicioLogin = servicioLogin;
        this.servicioMembresia = servicioMembresia;
    }





    @RequestMapping("/login")
    public ModelAndView irALogin() {

        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("login", modelo);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
        if (usuarioBuscado != null) {
            // Imprimir el estado del usuario para depuración
            System.out.println("Estado del usuario: " + usuarioBuscado.getEstado());

            request.getSession().setAttribute("usuario", usuarioBuscado);
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());

            if (usuarioBuscado.getRol().equals("ADMIN")) {
                modelAndView.setViewName("redirect:/administrador");
            } else if (usuarioBuscado.getRol().equals("PROFESIONAL")) {
                    modelAndView.setViewName("redirect:/homeProfesional");
            } else if (usuarioBuscado.getRol().equals("USUARIO")) {
                // Verificar el estado del usuario
                if ( usuarioBuscado.getMembresia()==null) {
                    modelAndView.setViewName("redirect:/bienvenido");
                } else if (usuarioBuscado.getMembresia().getEstado().equals(Estado.ACTIVADA)) {
                    modelAndView.setViewName("redirect:/usuarioMembresia");
                } else {
                    // Manejar otros estados si es necesario
                    modelAndView.setViewName("redirect:/bienvenido");
                }
            } else {
                // Manejar otros roles si es necesario
                modelAndView.setViewName("redirect:/bienvenido");
            }
        } else {
            modelAndView.setViewName("redirect:/login");
        }

        return modelAndView;
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
            modelAndView.setViewName("bienvenido");
        } else {
            // Manejar el caso en el que no se pudo obtener el usuario
            modelAndView.setViewName("redirect:/error"); // Redirigir a una página de error si es necesario
        }

        return modelAndView;
    }




    @GetMapping(path = "/modificarUsuario/{id}")
    public ModelAndView irAModificarDatos(Usuario usuario , Model model) throws UsuarioInexistente {
        servicioLogin.buscarUsuarioPorId(usuario.getId());
        model.addAttribute("usuario", usuario);
        return new ModelAndView("modificarUsuario");
    }

    @PostMapping(path = "/guardar-usuario")
    public ModelAndView actualizarUusario(@ModelAttribute Usuario usuario) throws UsuarioInexistente {
        Usuario buscado= servicioLogin.buscarUsuarioPorId(usuario.getId());
        try {
            servicioLogin.actualizarUsuario(buscado);
        } catch (UsuarioExistente e) {
            throw new RuntimeException(e);
        }


        return new ModelAndView("redirect:/login");
    }


    //creo un hijo
    @RequestMapping(path = "/nuevoHijo")
    public ModelAndView irAaLTA() {
        ModelMap modelo = new ModelMap();
        modelo.put("hijo", new Hijo());
        return new ModelAndView("nuevoHijo", modelo);

    }

    //creo un hijo
    @RequestMapping(path = "/guardar-hijos")
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

            // Actualizar el usuario después de registrar el hijo
            usuario = servicioLogin.buscarUsuarioPorId(usuario.getId()); // Actualiza el usuario desde la fuente de datos

            DatosMembresia membresia = usuario.getMembresia();

            // Verificar la membresía del usuario
            if (membresia != null && membresia.getEstado().equals(Estado.ACTIVADA)) {
                // Si tiene membresía activada, redireccionar a usuariomembresia
                List<Hijo> hijosActualizados = servicioLogin.buscarHijosPorId(usuario.getId());
                modelo.addAttribute("hijos", hijosActualizados);
                modelo.addAttribute("usuario", usuario);
                modelo.addAttribute("membresia", membresia);
                return new ModelAndView("usuarioMembresia", modelo);
            } else {
                // Si no tiene membresía activada, redireccionar a bienvenidos
                return new ModelAndView("bienvenido", modelo);
            }

        } catch (Exception e) {
            modelo.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("error", modelo); // Manejar cualquier otro error
        }
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