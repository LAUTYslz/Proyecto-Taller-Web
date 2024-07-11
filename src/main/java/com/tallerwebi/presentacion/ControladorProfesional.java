package com.tallerwebi.presentacion;

import com.tallerwebi.Dto.DiasAtencionDTO;
import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.util.List;

@Controller
public class ControladorProfesional {

    private final ServicioProfesional servicioContacto;
    private final ServicioLogin servicioLogin;
    private final ServicioMembresiaActivada servicioMembresiaActivada;

    @Autowired
    public ControladorProfesional(ServicioProfesional servicioContacto, ServicioLogin servicioLogin, ServicioMembresiaActivada servicioMembresiaActivada) {
        this.servicioContacto = servicioContacto;
        this.servicioLogin = servicioLogin;
        this.servicioMembresiaActivada = servicioMembresiaActivada;
    }

    @GetMapping("/homeProfesional")
    public ModelAndView mostrarBienvenido(Model model, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        // Obtener el usuario actual
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        // Agregar el usuario
        //modelAndView.addObject("usuario", usuario);
        //List<Etapa> etapas = servicioAdmi.listaDeEtapas();
        //List<Juego> juegos = servicioAdmi.listasDeJuegos();

        // Agregar las listas al modelo para que estén disponibles en la vista
        //model.addAttribute("etapas", etapas);
        //model.addAttribute("juegos", juegos);
        model.addAttribute("usuario", usuario);
        // Establecer la vista
        modelAndView.setViewName("home_profesional");

        return modelAndView;
    }


    @PostMapping("/profesionales")
    public String agregarContacto(Profesional contacto) {
        servicioContacto.guardar(contacto);
        return "redirect:/profesionales";
    }


    public List<Profesional> obtenerProfesionalesPorMetodo(String nombreMetodo) {
        return servicioContacto.traerProfesionalesPorMetodo(nombreMetodo);
    }

    public List<Profesional> obtenerProfesionalesPorTipo(String nombreTipo) {
        return servicioContacto.traerProfesionalesPorTipo(nombreTipo);
    }

    public List<Profesional> obtenerProfesionales() {
        return servicioContacto.traerProfesionales();
    }

    public List<Profesional> obtenerProfesionalesPorMetodoYTipo(String nombreTipo, String nombreMetodo) {
        return servicioContacto.traerProfesionalesPorTipoYMetodo(nombreTipo, nombreMetodo);
    }

    @GetMapping("/profesional")
    public ModelAndView mostrarPaginaProfesionales(
            @RequestParam(value = "metodo", required = false) String nombreMetodo,
            @RequestParam(value = "tipo", required = false) String nombreTipo) {
        ModelAndView mav = new ModelAndView("profesional");

        try {
            List<Profesional> profesionales = obtenerProfesionalesPorMetodoYTipo(nombreTipo, nombreMetodo);

            mav.addObject("profesionales", profesionales);
            mav.addObject("metodos", servicioContacto.traerTodosLosMetodos());
            mav.addObject("tipos", servicioContacto.traerTodosLosTipos());
        } catch (Exception e) {
            mav.addObject("error", e.getMessage());

        }
        return mav;
    }


    @GetMapping("/filtrar")
    public ModelAndView filtrarContactosPorMetodoYTipo(String nombreMetodo, String nombreTipo) {
        ModelAndView mav = new ModelAndView("/profesional/filtrarPorMetodoYTipo");
        mav.addObject("ContactosFiltradosPorTipoYMetodo", servicioContacto.traerProfesionalesPorTipoYMetodo(nombreTipo, nombreMetodo));
        return mav;
    }

    ////////////////////////////controlador consultas profesionales ////////////////////
    @GetMapping("/gestionarConsultas")
    public ModelAndView mostrarPaginaProfesionalesParaConsultas(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("gestionarConsultas");

        // Obtener el usuario actual (que es un Profesional)
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);
        mav.addObject("usuario", usuario);

        // Obtener las consultas asociadas al profesional
        String profesional = usuario.getEmail();
        List<Consulta> consultas = servicioMembresiaActivada.buscarConsultasPorProfesionales(profesional);
        mav.addObject("consultas", consultas);

        return mav;
    }


    @GetMapping("/responderConsulta/{id}")
    public ModelAndView verDetallesHijoYRespuesta(@PathVariable Long id, @RequestParam Long consultaId) {
        ModelAndView mav = new ModelAndView("responderConsulta");


        Hijo hijo = servicioLogin.busquedahijo(id);
        Consulta consulta = servicioMembresiaActivada.obtenerConsultaPorId(consultaId);

        mav.addObject("hijo", hijo);
        mav.addObject("consulta", consulta);

        return mav;
    }

    @PostMapping("/respuesta")
    public String responderConsulta(@RequestParam Long consultaId, @RequestParam String respuesta) {
        // Aquí deberías implementar la lógica para guardar la respuesta en la consulta correspondiente
        Consulta consulta = servicioMembresiaActivada.obtenerConsultaPorId(consultaId);
        servicioMembresiaActivada.respuestaDeProfesionalAConsulta(consultaId, respuesta);

        return "redirect:/gestionarConsultas";  // Redirigir a la página de gestión de consultas después de responder
    }

    @GetMapping("/verRespuesta/{id}")
    public ModelAndView verRespuestaConsulta(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("verRespuesta");

        // Aquí obtienes la consulta por su ID
        Consulta consulta = servicioMembresiaActivada.obtenerConsultaPorId(id);

        mav.addObject("consulta", consulta);

        return mav;

    }


    @GetMapping("/verRespuesta")
    public ModelAndView ver(@PathVariable Long id, @RequestParam Long consultaId) {
        ModelAndView mav = new ModelAndView("verRespuesta");

        // Aquí obtienes la consulta por su ID
        Consulta consulta = servicioMembresiaActivada.obtenerConsultaPorId(consultaId);

        mav.addObject("consulta", consulta);

        return mav;
    }


    @GetMapping("/setearDiasAtencion")
    public ModelAndView setearDiasAtencion(HttpServletRequest request) {
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);
        String profesionalMail = usuario.getEmail();
        Profesional profesional = servicioContacto.traerPorEmail(profesionalMail);

        ModelAndView mav = new ModelAndView();
        DiasAtencionDTO diasAtencionDTO = new DiasAtencionDTO();
        diasAtencionDTO.setIdProfesional(profesional.getId());
        mav.addObject("diasAtencionDTO", diasAtencionDTO);
        mav.addObject("profesional", profesional);
        mav.setViewName("formulario_setear_dias_atencion");
        return mav;
    }

    @PostMapping("/setearDiasAtencion")
    public ModelAndView setearDiasAtencion(@ModelAttribute DiasAtencionDTO diasAtencionDTO) {
        ModelAndView mav = new ModelAndView();

        Long idProfesional = diasAtencionDTO.getIdProfesional();
        String diaSemanaStr = diasAtencionDTO.getDiaSemana();
        DiasSemana diaSemanaEnum;
        try {
            diaSemanaEnum = DiasSemana.valueOf(diaSemanaStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            mav.addObject("error", e.getMessage());
            mav.setViewName("formulario_setear_dias_atencion");
            return mav;
        }
        LocalTime horaDesde = diasAtencionDTO.getHoraDesde();
        LocalTime horaHasta = diasAtencionDTO.getHoraHasta();
        int duracionSesiones = diasAtencionDTO.getDuracionSesiones();

        Profesional profesional = servicioContacto.obtenerPorId(idProfesional);

        servicioContacto.guardarDiasAtencion(profesional, diaSemanaEnum, horaDesde, horaHasta, duracionSesiones);

        mav.setViewName("redirect:/gestionarTurnos");
        return mav;

    }
}
