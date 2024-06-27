package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

import static com.tallerwebi.dominio.Estado.INACTIVA;

@Entity @Getter
@Setter
public class DatosCompra {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "idCompra")
    private Compra compra;

    private String nombre;

    private String email;

    private Long numDoc;

    private Long celular;

    private Date fecha;

    private String direccion;

    @ManyToOne
    private Tarjeta tarjeta;

    public DatosCompra() {
        this.fecha = new Date();
    }

}
