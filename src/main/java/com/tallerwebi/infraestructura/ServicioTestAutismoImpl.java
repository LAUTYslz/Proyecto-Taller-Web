package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioTestAutismo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ServicioTestAutismo")
@Transactional

public class ServicioTestAutismoImpl implements ServicioTestAutismo {
    @Override
    public String calcularResultado(String[] respuestas) {
        int respuestasSi = 0;
        for ( String respuesta : respuestas) {
            if (respuesta.equalsIgnoreCase("si")) {
                respuestasSi++;
            }
        }

        if (respuestasSi >= 6) {
            return "Tu niño podría tener síntomas de autismo o TDA. Es recomendable consultar a un profesional.";
        } else {
            return "Tu niño no tiene síntomas de autismo o TDA.";
        }
    }
}
