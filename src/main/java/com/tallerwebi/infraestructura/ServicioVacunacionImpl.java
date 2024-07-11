package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioVacunacion;
import com.tallerwebi.dominio.Vacunacion;
import com.tallerwebi.dominio.enums.Vacuna;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServicioVacunacionImpl {

    @Autowired
    private RepositorioVacunacion repositorioVacunacion;


    public List<Vacuna> obtenerVacunasPendientes(Date fechaNacimiento, List<Vacunacion> vacunaciones) {
        List<Vacuna> todasVacunas = Arrays.asList(Vacuna.values());
        List<Vacuna> vacunasPendientes = new ArrayList<>();

        Calendar fechaNacimientoCal = Calendar.getInstance();
        fechaNacimientoCal.setTime(fechaNacimiento);

        for (Vacuna vacuna : todasVacunas) {
            Calendar fechaVacunacion = (Calendar) fechaNacimientoCal.clone();
            fechaVacunacion.add(Calendar.MONTH, vacuna.getEdadMeses());

            boolean yaVacunado = vacunaciones.stream()
                    .anyMatch(v -> v.getVacuna().equals(vacuna));

            Calendar fechaActual = Calendar.getInstance();
            if (!yaVacunado && fechaVacunacion.getTime().before(fechaActual.getTime())) {
                vacunasPendientes.add(vacuna);
            }
        }

        return vacunasPendientes;
    }

    public List<Vacuna> obtenerProximaVacuna(Date fechaNacimiento, List<Vacunacion> vacunaciones) {
        List<Vacuna> todasVacunas = Arrays.asList(Vacuna.values());

        Calendar fechaNacimientoCal = Calendar.getInstance();
        fechaNacimientoCal.setTime(fechaNacimiento);

        List<Vacuna> vacunasOrdenadas = todasVacunas.stream()
                .sorted(Comparator.comparingInt(Vacuna::getEdadMeses))
                .collect(Collectors.toList());

        for (Vacuna vacuna : vacunasOrdenadas) {
            Calendar fechaVacunacion = (Calendar) fechaNacimientoCal.clone();
            fechaVacunacion.add(Calendar.MONTH, vacuna.getEdadMeses());

            boolean yaVacunado = vacunaciones.stream()
                    .anyMatch(v -> v.getVacuna().equals(vacuna));

            Calendar fechaActual = Calendar.getInstance();
            if (!yaVacunado && fechaVacunacion.getTime().after(fechaActual.getTime())) {
                return List.of(vacuna);
            }
        }

        return new ArrayList<>();
    }
}