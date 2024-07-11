package com.tallerwebi.dominio;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Caja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date fecha;
    private Integer ingreso;
    private Integer egreso;
    private Integer saldoActual;
    private String descripcion;
    @OneToMany(mappedBy = "caja", fetch = FetchType.EAGER)
    private List<Pago> pagos = new ArrayList<>();


    public Integer getIngreso() {
        return ingreso;
    }

    public void setIngreso(Integer ingreso) {
        this.ingreso = ingreso;
        calcularSaldoActual();
    }

    public Integer getEgreso() {
        return egreso;
    }

    public void setEgreso(Integer egreso) {
        this.egreso = egreso;
        calcularSaldoActual();
    }

    public Integer getSaldoActual()
    {

        return saldoActual;
    }

    public void setSaldoActual(Integer saldoActual) {
        this.saldoActual = saldoActual;
    }

    public void calcularSaldoActual() {

        if (this.ingreso != null) {
            saldoActual += this.ingreso;
        }
        if (this.egreso != null) {
            saldoActual -= this.egreso;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }
    public void agregarPago(Pago pago) {
        pagos.add(pago);
        pago.setCaja(this);
    }

}


