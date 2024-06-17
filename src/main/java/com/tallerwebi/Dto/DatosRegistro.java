package com.tallerwebi.Dto;

public class DatosRegistro {

    private String nombre;
    private String mail;
    private String password;
    private String repeticion;
    private String direccion;

    public DatosRegistro( String mail, String password,String repeticion, String nombre, String direccion ) {
        this.nombre = nombre;
        this.mail = mail;
        this.password = password;
        this.repeticion = repeticion;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getRepeticion() {
        return repeticion;
    }
}