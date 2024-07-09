package com.tallerwebi.dominio;

public interface ServicioCorreo {
    void enviarCorreo(String email, String asunto, String cuerpo);
}
