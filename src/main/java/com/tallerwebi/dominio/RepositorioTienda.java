package com.tallerwebi.dominio;
import java.util.List;

public interface RepositorioTienda {

    public List<Tienda> getAllTiendas();
    public Tienda obtenerInfoTienda(Long id);
    public void agregarTienda(Tienda tienda);
    public void eliminarTienda(Long id);


}
