package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.RepositorioProducto;
import com.tallerwebi.dominio.RepositorioTienda;
import com.tallerwebi.dominio.ServicioProducto;
import com.tallerwebi.dominio.ServicioTienda;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorTienda {
    private final ServicioTienda servicioTienda;
    private final ServicioProducto servicioProducto;
    private final RepositorioTienda repositorioTienda;
    private final RepositorioProducto repositorioProducto;

    public ControladorTienda(ServicioTienda servicioTienda, ServicioProducto servicioProducto, RepositorioTienda repositorioTienda, RepositorioProducto repositorioProducto) {
        this.servicioTienda = servicioTienda;
        this.servicioProducto = servicioProducto;
        this.repositorioTienda = repositorioTienda;
        this.repositorioProducto = repositorioProducto;
    }



}
