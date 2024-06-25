package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class ControladorTurno {
    private final ServicioTurno servicioTurno;
    private final ServicioProfesional servicioProfesional;
    private final ServicioLogin servicioLogin;

    @Autowired
    public ControladorTurno(ServicioTurno servicioTurno, ServicioProfesional servicioProfesional, ServicioLogin servicioLogin) {
        this.servicioTurno = servicioTurno;
        this.servicioProfesional = servicioProfesional;
        this.servicioLogin = servicioLogin;
    }

    @GetMapping("/agendar")
    public ModelAndView mostrarFormularioAgendarTurno(HttpServletRequest request) {
        Usuario usuario =  servicioLogin.obtenerUsuarioActual(request);

        if (usuario == null) {
            // Redirige a la página de inicio de sesión si el usuario no está autenticado
            return new ModelAndView("redirect:/login");
        }
        Long usuarioId = usuario.getId();

        ModelAndView mav = new ModelAndView("formulario_agendar_turno");
        List<Profesional> profesionales = servicioProfesional.traerProfesionalesSinTienda();
        mav.addObject("profesionales", profesionales);
        mav.addObject("usuarioId", usuarioId);
        return mav;
    }

    @PostMapping("/agendar")
    public ModelAndView agendarTurno(@RequestParam("profesionalId") Long profesionalId,
                                     @RequestParam("fechaHora") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date fechaHora,
                                     HttpServletRequest request) {
        Usuario usuario =  servicioLogin.obtenerUsuarioActual(request);
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Long usuarioId = usuario.getId();

        ModelAndView mav = new ModelAndView();
        try {
            servicioTurno.agendarTurno(usuarioId, profesionalId, fechaHora);
            mav.setViewName("redirect:/gestionarTurnos");
        } catch (RuntimeException e) {
            mav.setViewName("formulario_agendar_turno");
            mav.addObject("error", e.getMessage());
            List<Profesional> profesionales = servicioProfesional.traerProfesionalesSinTienda();
            mav.addObject("profesionales", profesionales);
            mav.addObject("usuarioId", usuarioId);
        }
        return mav;
    }

    @GetMapping("/gestionarTurnos")
    public ModelAndView verMisTurnos(HttpServletRequest request) {
        Usuario usuario =  servicioLogin.obtenerUsuarioActual(request);
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView mav = new ModelAndView();
        if(usuario.getRol().equals("PROFESIONAL")){
            String profesionalMail = usuario.getEmail();
            mav.setViewName("gestionar_turnos_profesional");
            try {
                List<Turno> turnos = servicioTurno.obtenerTurnosPorProfesional(profesionalMail);
                if (turnos.size() == 0) {
                    String mensaje = "El profesional no tiene turnos";
                    mav.addObject("mensaje", mensaje);
                }
                mav.addObject("turnos", turnos);
            }catch (RuntimeException e){
                mav.addObject("error", e.getMessage());
            }
        } else {
            Long usuarioId = usuario.getId();
            mav.setViewName("gestionar_turnos");
            List<Turno> turnos = servicioTurno.obtenerTurnosPorUsuario(usuarioId);
            if (turnos.size() == 0) {
                String mensaje = "El usuario no tiene turnos";
                mav.addObject("mensaje", mensaje);
            }
            mav.addObject("turnos", turnos);
            mav.addObject("usuarioId", usuarioId); // Para usar en el botón de agendar nuevo turno
        }


        return mav;
    }

    @GetMapping("/gestionarTurnosProfesional")
    public ModelAndView verMisTurnosProfesional(HttpServletRequest request) {
        Usuario usuario =  servicioLogin.obtenerUsuarioActual(request);
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }
        String profesionalMail = usuario.getEmail();

        ModelAndView mav = new ModelAndView("gestionar_turnos_profesional");
        try {
            List<Turno> turnos = servicioTurno.obtenerTurnosPorProfesional(profesionalMail);
            if (turnos.size() == 0) {
                String mensaje = "El usuario no tiene turnos";
                mav.addObject("mensaje", mensaje);
            }
            mav.addObject("turnos", turnos);
        }catch (RuntimeException e){
            mav.addObject("error", e.getMessage());
        }
        return mav;
    }


    @PostMapping("/actualizarEstadoTurno")
    public String actualizarEstadoTurno(@RequestParam Long turnoId,
                                        @RequestParam EstadoTurno nuevoEstado,
                                        HttpServletRequest request) {
        Usuario usuario =  servicioLogin.obtenerUsuarioActual(request);
        if (usuario == null) {
            return "redirect:/login";
        }

        servicioTurno.actualizarEstadoTurno(turnoId, nuevoEstado);
        return "redirect:/gestionarTurnos";
    }

    @PostMapping("/eliminarTurno")
    public String eliminarTurno(@RequestParam Long turnoId,
                                HttpServletRequest request) {
        Usuario usuario =  servicioLogin.obtenerUsuarioActual(request);
        if (usuario == null) {
            return "redirect:/login";
        }

        servicioTurno.eliminarTurno(turnoId);
        return "redirect:/gestionarTurnos";
    }

}
