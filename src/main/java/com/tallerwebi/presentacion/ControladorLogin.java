package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;
    private ServicioMembresia servicioMembresia;

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
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
        if (usuarioBuscado != null) {
            request.getSession().setAttribute("usuario", usuarioBuscado); // Guardar el objeto Usuario completo en la sesión
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
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


    @RequestMapping(path = "/info")
    public ModelAndView irAInfo() {
        return new ModelAndView("info");
    }


    @RequestMapping(path = "/info/doman")
    public ModelAndView irAInfoDoman() {
        return new ModelAndView("doman");
    }

    @RequestMapping(path = "/info/montessori", method = RequestMethod.GET)
    public ModelAndView irAInfoMontessori() {
        return new ModelAndView("montessori");
    }

    @RequestMapping(path = "/info/waldorf", method = RequestMethod.GET)
    public ModelAndView irAInfoWaldorf() {
        return new ModelAndView("waldorf");
    }


    @RequestMapping("/suscripcion")
    public ModelAndView irASuscripcion() {
        return new ModelAndView("suscripcion");
    }


    @RequestMapping(path = "/bienvenido")
    public ModelAndView irABienvenido(HttpServletRequest request) {
        ModelAndView modelo = new ModelAndView();
        Usuario usuario = obtenerUsuarioActual( request);
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
    public String mostrarBienvenido() {

        return "bienvenido";
    }


    @GetMapping("/membresiaPaga")
    public String mostrarMembresia() {

        return "membresiaPaga";
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

    @RequestMapping("/membresiaPaga")
    public ModelAndView mostrarFormularioMembresia() {
        ModelAndView modelAndView = new ModelAndView("membresiaPaga");
        modelAndView.addObject("membresia", new Membresia()); // Agregar el objeto "membresia" al modelo
        return modelAndView;
    }

    @GetMapping("/confirmacionMembresia")
    public ModelAndView irAConfirmacionMembresia(Model model) {
        Membresia membresia = new Membresia(); // Crear una instancia de la clase Membresia
        model.addAttribute("membresia", membresia); // Agregar membresia al modelo

        return new ModelAndView("confirmacionMembresia");
    }

    @RequestMapping(path = "/procesarMembresiaPaga", method = RequestMethod.POST)
    public ModelAndView procesarDatosDeMembresiaPaga(@ModelAttribute("membresia") Membresia membresia) {
        ModelMap model = new ModelMap();

        try {

            servicioMembresia.darDeAltaMembresia(membresia);
            ModelAndView modelAndView = new ModelAndView("confirmacionMembresia");
            modelAndView.addObject("membresia", membresia); //

            return new ModelAndView("confirmacionMembresia");

        } catch (MembresiaExistente ex) {
            model.put("error", "Tu usuario ya cuenta con una membresía paga");
        } catch (TarjetaInvalida ex) {
            model.put("error", "El número de tarjeta es inválido");
        } catch (CodigoInvalido e) {
            model.put("error", "El código de seguridad es inválido");
        }


        return new ModelAndView("confirmacionMembresia");
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
    public ModelAndView guardarHijo(@ModelAttribute("hijo") Hijo hijo ,HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        try {

            Usuario usuario = obtenerUsuarioActual(request); // Implementa esto según tu lógica de autenticación

            // Asociar el hijo con el usuario
            hijo.setUsuario(usuario);
            servicioLogin.registrarHijo(hijo);
            modelo.put("hijo", hijo);
            modelo.put("usuario", usuario);

        } catch (Exception e) {
            modelo.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("bienvenido", modelo);
        }
        return new ModelAndView("guardar-hijo", modelo);
    }
    // Mostrar formulario para agregar cónyuge
    @GetMapping("/nuevoConyuge")
    public String mostrarFormularioAgregarConyuge(HttpServletRequest request, Model model) {
        Usuario usuario = obtenerUsuarioActual(request);
        model.addAttribute("usuario", usuario);
        model.addAttribute("conyuge", new Usuario());
        return "nuevoConyuge";
    }



    @PostMapping("/guardar-conyuge")
    public ModelAndView registrarConyuge(HttpServletRequest request, @ModelAttribute("conyuge") Usuario conyuge) {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelo = new ModelMap();

            Usuario usuarioActual = obtenerUsuarioActual(request);
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

    // Mostrar detalles del usuario (incluido el cónyuge)
    @GetMapping("/detalles-conyuge")
    public ModelAndView mostrarDetallesUsuario(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Usuario usuario = obtenerUsuarioActual(request);
        ModelMap modelo = new ModelMap();
        modelAndView.addObject("usuario", usuario);
        modelo.put( "usuario", usuario);
        modelAndView.setViewName("detalles-conyuge");
        return new ModelAndView("detalles-conyuge",modelo);
    }


    private Usuario obtenerUsuarioActual(HttpServletRequest request) {
        return (Usuario) request.getSession().getAttribute("usuario");
    }

}

