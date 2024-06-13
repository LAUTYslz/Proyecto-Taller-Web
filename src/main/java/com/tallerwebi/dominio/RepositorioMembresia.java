package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioMembresia {



    DatosMembresia buscarMembresia(Long id);

    void registrarMembresia(DatosMembresia membresia);


    void eliminarMembresia(Long id);

    List<DatosMembresia> listarMembresias();

    void guardarTarjeta(Tarjeta tarjeta);
}
