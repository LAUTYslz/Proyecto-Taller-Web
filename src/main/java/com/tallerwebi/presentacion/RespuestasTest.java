package com.tallerwebi.presentacion;
import java.util.HashMap;
import java.util.Map;



public class RespuestasTest {

    private Map<String, String> respuestas;

    public RespuestasTest() {
        respuestas = new HashMap<>();
    }

    public String getRespuesta(String pregunta) {
        return respuestas.get(pregunta);
    }

    public void setRespuesta(String pregunta, String respuesta) {
        respuestas.put(pregunta, respuesta);
    }

}
