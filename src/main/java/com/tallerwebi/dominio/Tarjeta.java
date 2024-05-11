package com.tallerwebi.dominio;

import java.util.Date;

public class Tarjeta {
    private Long numeroDeTarjeta;
    private Date fechaDeVencimiento;
    private Integer codigoDeSeguridad;

    public Tarjeta (Long numeroDeTarjeta, Date fechaDeVencimiento, Integer codigoDeSeguridad){
        this.numeroDeTarjeta = numeroDeTarjeta;
        this.fechaDeVencimiento = fechaDeVencimiento;
        this.codigoDeSeguridad = codigoDeSeguridad;
    }

    public Long getNumeroDeTarjeta() {
        return this.numeroDeTarjeta;
    }

    public Date getFechaDeVencimiento(){
        return this.fechaDeVencimiento;
    }

    public Integer getCodigoDeSeguridad() {
        return this.codigoDeSeguridad;
    }


}
