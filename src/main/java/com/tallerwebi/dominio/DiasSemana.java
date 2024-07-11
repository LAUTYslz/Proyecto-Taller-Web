package com.tallerwebi.dominio;

public enum DiasSemana {
    DOMINGO(1),
    LUNES(2),
    MARTES(3),
    MIERCOLES(4),
    JUEVES(5),
    VIERNES(6),
    SABADO(7);

    private final int valorEnum;

    DiasSemana(int valorEnum) {
        this.valorEnum = valorEnum;
    }

    public int getValorEnum() {
        return valorEnum;
    }
}
