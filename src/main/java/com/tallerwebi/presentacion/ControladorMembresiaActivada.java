package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioInexistente;
import com.tallerwebi.infraestructura.RepositorioMetodoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

            Etapa etapa = null;
            Metodo metodo = null;

            // Verificar si hay al menos un hijo para obtener etapa y método
            if (!hijos.isEmpty()) {
                // Obtener la etapa y el método del primer hijo si la lista no está vacía
                Hijo primerHijo = hijos.get(0);
                etapa = primerHijo.getEtapa();
                metodo = primerHijo.getMetodo();
            }

            Consulta consulta =new Consulta();

            actualizarSesion(request, usuario, hijos,consulta);
            // Agregar el usuario, la membresía, la lista de hijos, la etapa y el método al modelo
            modelAndView.addObject("usuario", usuario);
            modelAndView.addObject("membresia", membresia);
            modelAndView.addObject("hijos", hijos);
            modelAndView.addObject("etapa", etapa);
            modelAndView.addObject("metodo", metodo); // Agregar el método al modelo
            modelAndView.addObject("consulta",consulta); // Objeto para almacenar la consulta

            // Establecer la vista como "usuarioMembresia"
            modelAndView.setViewName("usuarioMembresia");
        } else {
            // Manejar el caso en el que no se pudo obtener el usuario
            modelAndView.setViewName("redirect:/error"); // Redirigir a una página de error si es necesario
        }

        return modelAndView;
    }




    // Método para actualizar la sesión con los datos actualizados
    private void actualizarSesion(HttpServletRequest request, Usuario usuario, List<Hijo> hijos, Consulta consulta) {
        request.getSession().setAttribute("usuario", usuario);
        request.getSession().setAttribute("hijos", hijos);
        request.getSession().setAttribute("consulta", consulta);
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
        ModelAndView modelAndView = new ModelAndView();

        // Obtener el usuario actual
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        // Buscar el hijo por su ID
        Hijo hijo = servicioLogin.busquedahijo(hijoId);

        if (usuario != null && hijo != null) {
            // Asociar el método al hijo
            hijo = servicioMetodo.asociarHijo(hijoId, metodo_id);

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
            modelAndView.setViewName("redirect:/usuarioMembresia");
        } else {
            // Manejar el caso en el que no se pueda obtener el usuario o el hijo
            modelAndView.setViewName("redirect:/error");
        }

        return modelAndView;
    }

    @GetMapping
    public String listarProfesionales(Model model) {
        List<Profesional> profesionales = servicioProfesional.traerProfesionales();
        List<Metodo> metodos = servicioProfesional.traerTodosLosMetodos();
        List<TipoProfesional> tipos = servicioProfesional.traerTodosLosTipos();

        model.addAttribute("profesionales", profesionales);
        model.addAttribute("metodos", metodos);
        model.addAttribute("tipos", tipos);
        model.addAttribute("consulta", new Consulta()); // Objeto para almacenar la consulta

        return "realizarConsulta";
    }


    @PostMapping("/realizarConsulta")
    public String filtrarProfesionales(@ModelAttribute("consulta") Consulta consulta, Model model, HttpServletRequest request) {
        List<Profesional> profesionales = servicioProfesional.traerProfesionales();
        List<Metodo> metodos = servicioProfesional.traerTodosLosMetodos();
        List<TipoProfesional> tipos = servicioProfesional.traerTodosLosTipos();
        // Obtener la lista de hijos del usuario
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);
        List<Hijo> hijos = servicioLogin.buscarHijosPorId(usuario.getId());
        if (hijos == null) {
            // Si la lista de hijos es null, inicialízala como una lista vacía
            hijos = new ArrayList<>();
        }

        model.addAttribute("profesionales", profesionales);
        model.addAttribute("metodos", metodos);
        model.addAttribute("tipos", tipos);
        model.addAttribute("consulta", consulta);
        model.addAttribute("hijos", hijos);


        return "realizarConsulta";
    }


    @PostMapping("/seleccionDeProfesional")
    public ModelAndView procesarConsulta(@RequestParam("hijoId") Long hijoId, Model model) {
        ModelAndView modelAndView = new ModelAndView("realizarConsultaProfesional");

        // Lógica para buscar al hijo y obtener el método asociado
        Hijo buscarHijo = servicioLogin.busquedahijo(hijoId);
        String metodoAsociado = buscarHijo.getMetodo().getNombre();

        // Lógica para filtrar profesionales por el método asociado del hijo
        List<Profesional> profesionaless = servicioProfesional.traerProfesionales();
        List<TipoProfesional> tipos = servicioProfesional.traerTodosLosTipos();
        List<Profesional> filtro = servicioProfesional.traerProfesionalesPorMetodo(metodoAsociado);

        model.addAttribute("hijoId", hijoId);
        modelAndView.addObject("profesionales", filtro);
        modelAndView.addObject("metodo", metodoAsociado);
        modelAndView.addObject("hijo", buscarHijo);
        modelAndView.addObject("tipos", tipos);
        modelAndView.addObject("consulta", new Consulta()); // Objeto de consulta, si es necesario



        return modelAndView;
    }

    // Otros métodos del controlador pueden estar presentes aquí

  @PostMapping("/enviar-consulta")
    public ModelAndView enviarConsulta(@ModelAttribute("consulta") Consulta consulta,
                                       @RequestParam("hijoId") Long hijoId,
                                       @RequestParam("profesionalId") Long profesionalId, HttpServletRequest request) throws UsuarioInexistente {
        ModelAndView modelAndView = new ModelAndView("exito");

        // Lógica para buscar al hijo y asignarlo a la consulta

        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);
      List<Hijo> hijos = servicioLogin.buscarHijosPorId(usuario.getId());
        Hijo hijo = servicioLogin.buscarunhijoDeLaLista(hijos, hijoId);


      // Lógica para buscar al profesional y asignarlo a la consulta
        Profesional profesional = servicioProfesional.obtenerPorId(profesionalId);
        // Asignar el hijo y el profesional a la consulta



        // Aquí podrías realizar otras operaciones necesarias antes de guardar la consulta, como validar o procesar datos adicionales

        // Guardar la consulta
        Consulta guardadaConsulta = servicioMembresiaActivada.realizarConsulta(consulta, hijo,profesional,usuario); // Suponiendo que tienes un servicio para gestionar las consultas

        // Añadir objetos al modelo para pasar a la vista
        modelAndView.addObject("consulta", guardadaConsulta); // Pasar la consulta guardada a la vista
        modelAndView.addObject("profesionales", servicioProfesional.traerProfesionales()); // Obtener todos los profesionales para la vista
        modelAndView.addObject("hijos",hijos);

        return modelAndView;
    }

    @GetMapping("/irALaTienda")
    public ModelAndView irALaTienda(){
        return new ModelAndView("tiendaVirtual");
    }




}








