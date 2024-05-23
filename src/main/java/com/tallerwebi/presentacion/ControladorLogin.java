package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin){
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping("/login")
    public ModelAndView irALogin() {

        ModelMap modelo = new ModelMap();
        modelo.put("usuarioFormulario", new UsuarioFormulario());
        return new ModelAndView("login", modelo);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("usuarioFormulario") UsuarioFormulario usuarioFormulario, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        // Validar las credenciales del usuario principal
        Usuario usuarioPrincipal = servicioLogin.consultarUsuario(usuarioFormulario.getEmail(), usuarioFormulario.getPassword());
        Usuario conyuge = servicioLogin.consultarUsuario(usuarioFormulario.getEmail(), usuarioFormulario.getPassword());

        // Verificar si se pudo iniciar sesión como usuario principal
        if (usuarioPrincipal != null) {
            model.addAttribute("usuario", usuarioPrincipal);
            request.getSession().setAttribute("ROL", usuarioPrincipal.getRol());
            return new ModelAndView("bienvenido", model);
        }

        // Verificar si se pudo iniciar sesión como cónyuge
        if (conyuge != null) {
            model.addAttribute("usuario", conyuge);
            request.getSession().setAttribute("ROL", conyuge.getRol());
            return new ModelAndView("bienvenido", model);
        }

        // Si no se pudo iniciar sesión ni como usuario principal ni como cónyuge, mostrar un mensaje de error
        model.put("error", "Usuario o clave incorrecta");
        return new ModelAndView("login", model);
    }
    @RequestMapping(path = "/registrarse", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") UsuarioFormulario usuarioFormulario) {
        ModelMap model = new ModelMap();
        try {
            // Crear el usuario principal
            Usuario usuario = new Usuario();
            usuario.setEmail(usuarioFormulario.getEmail());
            usuario.setPassword(usuarioFormulario.getPassword());
            usuario.setNombre(usuarioFormulario.getNombre());

            // Crear y asignar el cónyuge si se proporcionan sus datos
            if (usuarioFormulario.getEmailConyuge() != null && usuarioFormulario.getPasswordConyuge() != null && usuarioFormulario.getNombreConyuge() != null) {
                Usuario conyuge = new Usuario();
                conyuge.setEmailConyuge(usuarioFormulario.getEmailConyuge());
                conyuge.setPasswordConyuge(usuarioFormulario.getPasswordConyuge());
                conyuge.setNombreConyuge(usuarioFormulario.getNombreConyuge());

                // Establecer la relación entre el usuario principal y su cónyuge
                usuario.setConyuge(conyuge);

            }

            // Registrar tanto al usuario principal como al cónyuge
            servicioLogin.registrar(usuario);


            // Si hay cónyuge, también regístralo
            if (usuario.getConyuge() != null) {
                servicioLogin.registrar(usuario.getConyuge());
            }

        } catch (UsuarioExistente e) {
            model.put("error", "El usuario ya existe, ¿olvidó su contraseña?");
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


    @RequestMapping(path="/info")
    public ModelAndView irAInfo(){
        return new ModelAndView("info");
    }


    @RequestMapping(path = "/info/doman")
    public ModelAndView irAInfoDoman(){ return new ModelAndView("doman");}

    @RequestMapping(path = "/info/montessori", method = RequestMethod.GET)
    public ModelAndView irAInfoMontessori() {
        return new ModelAndView("montessori");
    }

    @RequestMapping(path = "/info/waldorf", method = RequestMethod.GET)
    public ModelAndView irAInfoWaldorf() { return new ModelAndView("waldorf");}


    @RequestMapping("/suscripcion")
    public ModelAndView irASuscripcion() {
        return new ModelAndView("suscripcion");
    }

    @RequestMapping(path = "/bienvenido")
    public ModelAndView irABienvenido() {
        return new ModelAndView("bienvenido");
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

            Usuario usuario= servicioLogin.buscarUsuarioPorId(id);
            model.put("usuario", usuario);
        } catch (UsuarioInexistente e){
            model.put("error", "El usuario es inexistente");
            return new ModelAndView("bienvenido", model);
        } catch (Exception e){
            model.put("error", "Error al buscar el  usuario");
            return new ModelAndView("bienvenido", model);
        }

        return new ModelAndView("modificarUsuario", model);
    }


    //creo un hijo
 /*   @RequestMapping(path = "/nuevos")
    public ModelAndView irAaLTA() {
        ModelMap modelo = new ModelMap();
        modelo.put("hijo", new Hijo());
        return new ModelAndView("nuevos", modelo);

    }*/

  /*  @RequestMapping(path = "/guardar-hijo", method = RequestMethod.POST)
    public ModelAndView guardarHijo(@ModelAttribute("hijo") Usuario usuario , HttpServletRequest request, Usuario hijo) {
        ModelMap modelo = new ModelMap();
        try{
            servicioLogin.registrarHijo(usuario,hijo);
        } catch (UsuarioInexistente e){
            modelo.put("error", "El usuario no existe");
            return new ModelAndView("nuevos", modelo);
        } catch (Exception e){
            modelo.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("nuevo-nuevos", modelo);
        }
        return new ModelAndView("redirect:/home");*/
    }









