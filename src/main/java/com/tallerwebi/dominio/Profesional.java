package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class Profesional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String telefono;
    private String email;
    private String direccion;
    private String institucion;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private TipoProfesional tipo;

    @ManyToOne
    @JoinColumn(name = "metodo_id")
    private Metodo metodo;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_atencion")
    private DiasSemana diaAtencion;

    @Column(name = "hora_desde")
    private LocalTime horaDesde;

    @Column(name = "hora_hasta")
    private LocalTime horaHasta;

    @Column(name = "duracion_sesiones")
    private Integer duracionSesiones;


    private Integer valorConsulta = 10000;

    public DiasSemana getDiaAtencion() {
        return diaAtencion;
    }

    public void setDiaAtencion(DiasSemana diaAtencion) {
        this.diaAtencion = diaAtencion;
    }

    public LocalTime getHoraDesde() {
        return horaDesde;
    }

    public void setHoraDesde(LocalTime horaDesde) {
        this.horaDesde = horaDesde;
    }

    public LocalTime getHoraHasta() {
        return horaHasta;
    }

    public void setHoraHasta(LocalTime horaHasta) {
        this.horaHasta = horaHasta;
    }

    public Integer getDuracionSesiones() {
        return duracionSesiones;
    }

    public void setDuracionSesiones(Integer duracionSesiones) {
        this.duracionSesiones = duracionSesiones;
    }

    public Integer getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(Integer valorConsulta) {
        this.valorConsulta = valorConsulta;
    }

    public Profesional() {
    }


    public TipoProfesional getTipo() {
        return tipo;
    }

    public void setTipo(TipoProfesional tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public Metodo getMetodo() {
        return metodo;
    }

    public void setMetodo(Metodo metodo) {
        this.metodo = metodo;
    }
}
