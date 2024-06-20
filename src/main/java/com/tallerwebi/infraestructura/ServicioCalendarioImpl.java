package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioCelndario;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioCalendario")
@Transactional
public class ServicioCalendarioImpl implements ServicioCelndario {
}
