package com.tallerwebi.dominio;

<<<<<<< HEAD
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Entity
public class Tarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
=======
import java.util.Date;

public class Tarjeta {
>>>>>>> ana
    private Long numeroDeTarjeta;
    private Date fechaDeVencimiento;
    private Integer codigoDeSeguridad;

<<<<<<< HEAD

=======
    public Tarjeta(){

    }

    public Tarjeta (Long numeroDeTarjeta, Date fechaDeVencimiento, Integer codigoDeSeguridad){
        this.numeroDeTarjeta = numeroDeTarjeta;
        this.fechaDeVencimiento = fechaDeVencimiento;
        this.codigoDeSeguridad = codigoDeSeguridad;
    }
>>>>>>> ana

    public Long getNumeroDeTarjeta() {
        return this.numeroDeTarjeta;
    }

    public void setNumeroDeTarjeta(Long numeroDeTarjeta){
        this.numeroDeTarjeta = numeroDeTarjeta;
    }

    public Date getFechaDeVencimiento(){
        return this.fechaDeVencimiento;
    }

    public void setFechaDeVencimiento(Date fechaDeVencimiento){
        this.fechaDeVencimiento = fechaDeVencimiento;
    }

    public Integer getCodigoDeSeguridad() {
        return this.codigoDeSeguridad;
    }

    public void setCodigoDeSeguridad(Integer codigoDeSeguridad) {
        this.codigoDeSeguridad = codigoDeSeguridad;
    }

<<<<<<< HEAD

=======
>>>>>>> ana
}
