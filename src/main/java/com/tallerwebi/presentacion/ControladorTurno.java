package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
public class ControladorTurno {
    private final ServicioTurno servicioTurno;
    private final ServicioProfesional servicioProfesional;

    @Autowired
    public ControladorTurno(ServicioTurno servicioTurno, ServicioProfesional servicioProfesional) {
        this.servicioTurno = servicioTurno;
        this.servicioProfesional = servicioProfesional;
    }

    @GetMapping("/agendar")
    public ModelAndView mostrarFormularioAgendarTurno() {
        ModelAndView mav = new ModelAndView("formulario_agendar_turno");
        List<Profesional> profesionales = servicioProfesional.traerProfesionales();
        mav.addObject("profesionales", profesionales);
        return mav;
    }

    @PostMapping("/agendar")
    public ModelAndView agendarTurno(@RequestParam("usuarioId") Long usuarioId,
                                     @RequestParam("profesionalId") Long profesionalId,
                                     @RequestParam("fechaHora") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date fechaHora) {
        ModelAndView mav = new ModelAndView();
        try {
            servicioTurno.agendarTurno(usuarioId, profesionalId, fechaHora);
            mav.setViewName("redirect:/turnos/misTurnos");
        } catch (Exception e) {
            mav.setViewName("formulario_agendar_turno");
            mav.addObject("error", e.getMessage());
        }
        return mav;
    }

    @GetMapping("/misTurnos")
    public ModelAndView verMisTurnos(@RequestParam("usuarioId") Long usuarioId) {
        ModelAndView mav = new ModelAndView("lista_turnos");
        List<Turno> turnos = servicioTurno.obtenerTurnosPorUsuario(usuarioId);
        mav.addObject("turnos", turnos);
        return mav;
    }

    @PostMapping("/actualizarEstadoTurno")
    public String actualizarEstadoTurno(@RequestParam Long turnoId,
                                        @RequestParam EstadoTurno nuevoEstado) {
        servicioTurno.actualizarEstadoTurno(turnoId, nuevoEstado);
        return "redirect:/turnos";
    }

    /*
    @GetMapping("/turnos/presenciales")
    public ModelAndView mostrarTurnosPresenciales(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            // Redirige a la página de inicio de sesión si el usuario no está autenticado
            return new ModelAndView("redirect:/login");
        }

        Long usuarioId = usuario.getId();

        ModelAndView mav = new ModelAndView("turnos_presenciales");
        List<Turno> turnos = servicioTurno.obtenerTurnosPorUsuario(usuarioId);
        mav.addObject("turnos", turnos);
        mav.addObject("usuarioId", usuarioId); // Para usar en el botón de agendar nuevo turno
        return mav;
    }

    @GetMapping("/agendar")
    public ModelAndView mostrarFormularioAgendarTurno(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            // Redirige a la página de inicio de sesión si el usuario no está autenticado
            return new ModelAndView("redirect:/login");
        }

        Long usuarioId = usuario.getId();

        ModelAndView mav = new ModelAndView("formulario_agendar_turno");
        List<Profesional> profesionales = servicioProfesional.traerProfesionales();
        mav.addObject("profesionales", profesionales);
        mav.addObject("usuarioId", usuarioId);
        return mav;
    }

    @PostMapping("/agendar")
    public ModelAndView agendarTurno(@RequestParam("profesionalId") Long profesionalId,
                                     @RequestParam("fechaHora") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date fechaHora,
                                     HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            // Redirige a la página de inicio de sesión si el usuario no está autenticado
            return new ModelAndView("redirect:/login");
        }

        Long usuarioId = usuario.getId();

        ModelAndView mav = new ModelAndView();
        try {
            servicioTurno.agendarTurno(usuarioId, profesionalId, fechaHora);
            mav.setViewName("redirect:/turnos/presenciales");
        } catch (Exception e) {
            mav.setViewName("formulario_agendar_turno");
            mav.addObject("error", e.getMessage());
            List<Profesional> profesionales = servicioProfesional.traerProfesionales();
            mav.addObject("profesionales", profesionales);
            mav.addObject("usuarioId", usuarioId);
        }
        return mav;
    }

    @PostMapping("/actualizarEstadoTurno")
    public String actualizarEstadoTurno(@RequestParam Long turnoId,
                                        @RequestParam EstadoTurno nuevoEstado,
                                        HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        servicioTurno.actualizarEstadoTurno(turnoId, nuevoEstado);
        return "redirect:/turnos/presenciales";
    }

    @PostMapping("/eliminarTurno")
    public String eliminarTurno(@RequestParam Long turnoId,
                                HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        servicioTurno.eliminarTurno(turnoId);
        return "redirect:/turnos/presenciales";
    }
     */
}
