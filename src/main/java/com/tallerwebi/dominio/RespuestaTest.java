package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class RespuestaTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String texto;

    @ManyToOne
    @JoinColumn(name = "modeloTest_id", nullable = false)
    private ModeloTest modeloTest;

    public RespuestaTest() {
    }

    public RespuestaTest(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ModeloTest getModeloTest() {
        return modeloTest;
    }

    public void setModeloTest(ModeloTest modeloTest) {
        this.modeloTest = modeloTest;
    }
}
