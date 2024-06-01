package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioContacto {
    public void guardar(Contacto contacto);
    public List<Contacto> traerContactos();
}
