package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioTest;
import org.springframework.stereotype.Service;
import com.tallerwebi.presentacion.RespuestasTest;
import javax.transaction.Transactional;


@Service("servicioTest")
@Transactional
public class ServicioTestImpl implements ServicioTest {

    private RespuestasTest respuestasTest;

    @Override
    public String calcularResultado(RespuestasTest respuestasTest) {
        // Aquí realizas la lógica para calcular el resultado del test
        // Por ejemplo, contar cuántas respuestas afirmativas hay y dar un resultado según eso
        return "Tu niño no tiene síntomas de autismo o TDA.";
    }
}

