package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ResultadoProcesamiento;
import org.springframework.stereotype.Service;

@Service
public class ServicioProcesamientoRespuestas {

    public ResultadoProcesamiento procesarRespuestas(String respuestaPregunta1, String respuestaPregunta2, String respuestaPregunta3, String respuestaPregunta4, String respuestaPregunta5, String respuestaPregunta6, String respuestaPregunta7, String respuestaPregunta8, String respuestaPregunta9, String respuestaPregunta10 ) {

        int cantidadSi = contarRespuestasSi(respuestaPregunta1, respuestaPregunta2, respuestaPregunta3, respuestaPregunta4, respuestaPregunta5, respuestaPregunta6, respuestaPregunta7, respuestaPregunta8, respuestaPregunta9, respuestaPregunta10);

        // Devuelve el resultado del procesamiento
        return new ResultadoProcesamiento(cantidadSi);
    }

    private int contarRespuestasSi(String... respuestas) {
        int count = 0;
        for (String respuesta : respuestas) {
            if ("si".equalsIgnoreCase(respuesta)) {
                count++;
            }
        }
        return count;
    }
}
