package com.tallerwebi.dominio;


import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity @Getter
@Setter
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long numeroDeTarjeta;
    @DateTimeFormat(pattern = "yyy-mm-dd")
    private Date fechaDeVencimiento;
    private Integer codigoDeSeguridad;

    public Tarjeta (){

    }

}
