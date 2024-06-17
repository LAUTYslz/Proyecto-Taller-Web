package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioMembresia {

    DatosMembresia buscarMembresia(String email);
    void registrarMembresia(DatosMembresia membresia);
    void eliminarMembresia(String email);
    List<DatosMembresia> listarMembresias();

}
