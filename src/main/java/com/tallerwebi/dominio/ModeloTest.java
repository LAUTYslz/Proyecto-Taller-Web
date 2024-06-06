package com.tallerwebi.dominio;
import com.tallerwebi.presentacion.PreguntaTest;
import com.tallerwebi.presentacion.RespuestaTest;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.ArrayList;
import java.util.List;

public class ModeloTest {
    private List<PreguntaTest> preguntas;
    private List<RespuestaTest> respuestas;

    public ModeloTest() {
        this.preguntas = new ArrayList<>();
        this.respuestas = new ArrayList<>();
    }

    public List<PreguntaTest> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<PreguntaTest> preguntas) {

        this.preguntas = preguntas;
    }

    public List<RespuestaTest> getRespuestas() {

        return respuestas;
    }

    public void setRespuestas(List<RespuestaTest> respuestas) {

        this.respuestas = respuestas;
    }
}
