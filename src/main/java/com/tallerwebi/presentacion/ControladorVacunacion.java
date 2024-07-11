package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.enums.Vacuna;
import com.tallerwebi.infraestructura.ServicioVacunacionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ControladorVacunacion {

    @Autowired
    private ServicioLogin servicioLogin;

    @Autowired
    private RepositorioVacunacion repositorioVacunacion;

    @Autowired
    private ServicioVacunacionImpl servicioVacunacionImpl;

    @GetMapping("/vacunacionSeleccionar")
    public String seleccionarHijo(HttpServletRequest request, Model model) {
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);
        if (usuario == null) {
            return "redirect:/error";
        }

        List<Hijo> hijos = servicioLogin.buscarHijosPorId(usuario.getId());
        model.addAttribute("hijos", hijos);
        return "vacunacionSeleccionar";
    }

    @PostMapping("/cargarVacunas")
    public String cargarVacunas(@RequestParam Long hijoId, Model model) {
        Hijo hijo = servicioLogin.busquedahijo(hijoId);
        List<Vacuna> todasVacunas = Arrays.asList(Vacuna.values());
        List<Vacunacion> vacunaciones = repositorioVacunacion.obtenerVacunacionesPorHijo(hijoId);
        List<Vacuna> vacunasCargadas = vacunaciones.stream()
                .map(Vacunacion::getVacuna)
                .collect(Collectors.toList());

        model.addAttribute("hijo", hijo);
        model.addAttribute("todasVacunas", todasVacunas);
        model.addAttribute("vacunasCargadas", vacunasCargadas);
        return "cargarVacunas";
    }

    @PostMapping("/guardarVacunas")
    public String guardarVacunas(@RequestParam Long hijoId, @RequestParam(required = false) List<Vacuna> vacunas, RedirectAttributes redirectAttributes) {
        if (vacunas == null || vacunas.isEmpty()) {
            // Redirigir a la página de mostrar vacunas pendientes con un mensaje de advertencia
            redirectAttributes.addFlashAttribute("warning", "No se seleccionaron vacunas. A continuación se muestran las vacunas pendientes.");
            return "redirect:/mostrarVacunasPendientes?hijoId=" + hijoId;
        }

        List<Vacunacion> vacunacionesExistentes = repositorioVacunacion.obtenerVacunacionesPorHijo(hijoId);

        // Eliminar vacunaciones que no están en la lista de IDs seleccionados
        for (Vacunacion vacunacion : vacunacionesExistentes) {
            if (!vacunas.contains(vacunacion.getVacuna())) {
                repositorioVacunacion.eliminar(vacunacion);
            }
        }

        // Agregar nuevas vacunaciones para las vacunas seleccionadas
        for (Vacuna vacuna : vacunas) {
            boolean yaVacunado = vacunacionesExistentes.stream()
                    .anyMatch(v -> v.getVacuna().equals(vacuna));
            if (!yaVacunado) {
                Vacunacion vacunacion = new Vacunacion();
                vacunacion.setHijo(servicioLogin.busquedahijo(hijoId));
                vacunacion.setVacuna(vacuna);
                vacunacion.setFechaAdministracion(new Date());
                repositorioVacunacion.guardar(vacunacion);
            }
        }

        return "redirect:/mostrarVacunasPendientes?hijoId=" + hijoId;
    }

    @GetMapping("/mostrarVacunasPendientes")
    public String mostrarVacunasPendientes(@RequestParam Long hijoId, Model model, @RequestParam(required = false) String warning) {
        Hijo hijo = servicioLogin.busquedahijo(hijoId);
        List<Vacunacion> vacunaciones = repositorioVacunacion.obtenerVacunacionesPorHijo(hijoId);
        List<Vacuna> vacunasPendientes = servicioVacunacionImpl.obtenerVacunasPendientes(hijo.getFecha_nacimiento(), vacunaciones);
        List<Vacuna> proximaVacuna = servicioVacunacionImpl.obtenerProximaVacuna(hijo.getFecha_nacimiento(), vacunaciones);

        model.addAttribute("hijo", hijo);
        model.addAttribute("vacunasPendientes", vacunasPendientes);
        model.addAttribute("proximaVacuna", proximaVacuna);

        if (warning != null) {
            model.addAttribute("warningMessage", warning);
        }

        return "mostrarVacunasPendientes";
    }
}