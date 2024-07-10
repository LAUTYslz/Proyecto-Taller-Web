package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ModeloTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombreTest;

    private String descripcionTest;

    @OneToMany(mappedBy = "modeloTest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PreguntaTest> preguntas = new ArrayList<>();

    @OneToMany(mappedBy = "modeloTest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespuestaTest> respuestas = new ArrayList<>();
    public ModeloTest() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreTest() {
        return nombreTest;
    }

    public void setNombreTest(String nombreTest) {
        this.nombreTest = nombreTest;
    }

    public String getDescripcionTest() {
        return descripcionTest;
    }

    public void setDescripcionTest(String descripcionTest) {
        this.descripcionTest = descripcionTest;
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
