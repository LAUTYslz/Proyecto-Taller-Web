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

            Consulta consulta = new Consulta();
            if(consulta.getUsuario()==null){
                consulta =null;
            }
          Consulta consultaDeUsuario=  servicioMembresiaActivada.buscarConsultaPorUsuario(usuario.getId());
            actualizarSesion(request, usuario, hijos, consultaDeUsuario);
            // Agregar el usuario, la membresía, la lista de hijos, la etapa y el método al modelo
            modelAndView.addObject("usuario", usuario);
            modelAndView.addObject("membresia", membresia);
            modelAndView.addObject("hijos", hijos);
            modelAndView.addObject("etapa", etapa);
            modelAndView.addObject("metodo", metodo); // Agregar el método al modelo
            modelAndView.addObject("consulta", consultaDeUsuario); // Objeto para almacenar la consulta

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

    @GetMapping("/realizarConsulta")
    public String mostrarFormulario(Model model, HttpServletRequest request) {
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request); // Obtener el usuario actual (suponiendo que obtienes el usuario de alguna manera)

        List<Hijo> hijos = servicioLogin.buscarHijosPorId(usuario.getId());// Obtener los hijos del usuario actual
       String metodo =hijos.get(0).getMetodo().getNombre();
        List<Profesional> profesionales = servicioProfesional.traerProfesionalesPorMetodo(metodo) ;// Asegúrate de obtener los profesionales disponibles

        model.addAttribute("consulta", new Consulta()); // Objeto para almacenar la consulta
        model.addAttribute("hijos", hijos); // Agregar la lista de hijos al modelo
        model.addAttribute("profesionales", profesionales); // Agregar la lista de profesionales al modelo

        return "realizarConsulta";
    }




    @PostMapping("/enviar-consulta")
    public String enviarConsulta(@ModelAttribute("consulta") Consulta consulta,HttpServletRequest request) {
        // Aquí puedes acceder a los datos de consulta
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);
        Long hijoId = consulta.getHijo().getId();
        Long profesionalId = consulta.getProfesional().getId();
        String mensaje = consulta.getMensaje();

        // Realiza las operaciones necesarias, como guardar la consulta en la base de datos
        Consulta guardada = servicioMembresiaActivada.realizarConsulta(consulta, hijoId,profesionalId,usuario);
        return "exito"; // Nombre de la vista de éxito
    }

    @GetMapping("/irALaTienda")
    public ModelAndView irALaTienda(){
        return new ModelAndView("tiendaVirtual");
    }




}








