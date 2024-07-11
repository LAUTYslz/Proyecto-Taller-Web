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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
/*
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
*/
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
                List<Turno> turnos = servicioTurno.obtenerTurnosReservadosPorProfesional(profesionalMail);
                if (turnos == null || turnos.isEmpty()) {
                    String mensaje = "El profesional no tiene turnos";
                    mav.addObject("mensaje", mensaje);
                } else {
                    mav.addObject("turnos", turnos);
                    System.out.println("Turnos " + turnos.size());
                    System.out.println("Turnos " + turnos);
                }
                Integer montoACobrar = servicioProfesional.calcularMontoACobrar(profesionalMail);
                mav.addObject("montoACobrar", montoACobrar);
            }catch (RuntimeException e){
                mav.addObject("error", e.getMessage());
            }
        } else {
            Long usuarioId = usuario.getId();
            mav.setViewName("gestionar_turnos");
            try{
                List<Turno> turnos = servicioTurno.obtenerTurnosPorUsuario(usuarioId);
                if (turnos.size() == 0) {
                    String mensaje = "El usuario no tiene turnos";
                    mav.addObject("mensaje", mensaje);
                }
                mav.addObject("tipos", servicioProfesional.traerTodosLosTiposSinTienda());
                mav.addObject("turnos", turnos);
                mav.addObject("usuarioId", usuarioId); // Para usar en el botón de agendar nuevo turno
            }catch (RuntimeException e){
                mav.addObject("error", e.getMessage());
            }
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

            Integer montoACobrar = servicioProfesional.calcularMontoACobrar(profesionalMail);
            mav.addObject("montoACobrar", montoACobrar);
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

    @PostMapping("/cancelarTurno")
    public String cancelarturno(@RequestParam Long turnoId,
                                HttpServletRequest request) {
        Usuario usuario =  servicioLogin.obtenerUsuarioActual(request);
        if (usuario == null) {
            return "redirect:/login";
        }

        servicioTurno.cancelarTurno(turnoId);
        return "redirect:/gestionarTurnos";
    }

    @GetMapping("/generarTurnos")
    public ModelAndView generarTurnos(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        Usuario usuario =  servicioLogin.obtenerUsuarioActual(request);
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }
        mav.setViewName("formulario_generar_turnos");
        return mav;
    }

    @PostMapping("/generarTurnos")
    public ModelAndView  generarTurnos(@RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
                                       @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin)
    {
        ModelAndView modelAndView = new ModelAndView();
        if (fechaInicio.isAfter(fechaFin)) {
            modelAndView.addObject("error", "La fecha de inicio no puede ser posterior a la fecha de cierre.");
            modelAndView.setViewName("gestionarProfesionales"); // Asegúrate de que esto coincida con el nombre de tu vista
            return modelAndView;
        }
        try {
            servicioTurno.generarTurnos(fechaInicio, fechaFin);
            modelAndView.addObject("mensaje", "Turnos generados exitosamente.");
        } catch (Exception e) {
            modelAndView.addObject("error", "Ocurrió un error al generar los turnos: " + e.getMessage());
        }
        modelAndView.setViewName("gestionarProfesionales");
        return modelAndView;
    }

    @GetMapping("/turnos/agendarTurno")
    public ModelAndView agendarTurno(
            @RequestParam(value = "tipo", required = false) String nombreTipo,
            HttpServletRequest request)
    {
        Usuario usuario =  servicioLogin.obtenerUsuarioActual(request);
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView mav = new ModelAndView();
        try {
            List<Turno> turnos = servicioTurno.obtenerTurnosPorTipoProfesional(nombreTipo);
            if (turnos.isEmpty() || turnos == null) {
                String mensaje = "No hay turnos disponibles para esta especialidad";
                mav.addObject("mensaje", mensaje);
                turnos = new ArrayList<>(); // Inicializa una lista vacía si no hay turnos
            }
            mav.setViewName("listas_turnos_presencial");
            mav.addObject("turnos", turnos);
            mav.addObject("nombretipo", nombreTipo);
        }catch (RuntimeException e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("redirect:/gestionar");
        }
        return mav;
    }

    @PostMapping("/reservarTurno")
    public ModelAndView reservarTurno(@RequestParam("turno_id")Optional<Long> turnoId,HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
         Usuario usuario =  servicioLogin.obtenerUsuarioActual(request);
        if (!turnoId.isPresent()) {
            mav.addObject("error", "Debe seleccionar un turno antes de continuar.");
            mav.setViewName("/gestionar_turnos");
        }

        Long turnoSeleccionadoId = turnoId.get();
        try {
            servicioTurno.agendarTurno(usuario.getId(), turnoSeleccionadoId);
            mav.addObject("mensaje", "Turno agregado correctamente");
            mav.setViewName("redirect:/gestionarTurnos");
        }catch (RuntimeException e){
            mav.addObject("error", e.getMessage());
            mav.setViewName("gestionar_turnos");
        }
        return mav;
    }

}
