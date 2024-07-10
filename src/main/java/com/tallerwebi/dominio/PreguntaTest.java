package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class PreguntaTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String texto;

    @ManyToOne
    @JoinColumn(name = "modeloTest_id")
    private ModeloTest modeloTest;

    public PreguntaTest(String texto) {

        this.texto = texto;
    }

    public PreguntaTest() {

    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public ModeloTest getModeloTest() {
        return modeloTest;
    }

    public void setModeloTest(ModeloTest modeloTest) {
        this.modeloTest = modeloTest;
    }
}
