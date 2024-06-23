package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioProducto;
import com.tallerwebi.dominio.ServicioProducto;
import com.tallerwebi.dominio.excepcion.NoHayProductos;
import com.tallerwebi.dominio.excepcion.ProductoInexistente;
import com.tallerwebi.dominio.excepcion.StockInexistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioProducto")
@Transactional
public class ServicioProductoImpl implements ServicioProducto {
    private final RepositorioProducto repositorioProducto;


    public ServicioProductoImpl(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }


    @Override
    public Producto buscarProductoPorNombre(String nombre) throws ProductoInexistente {
        if (repositorioProducto.buscarProductoPorNombre(nombre) != null) {
            return repositorioProducto.buscarProductoPorNombre(nombre);
        } else {
            throw new ProductoInexistente();
        }
    }

    @Override
    public void agregarProducto(Producto producto) {
        this.repositorioProducto.agregarProducto(producto);
    }

    @Override
    public void actualizarProducto(Producto producto) {
        this.repositorioProducto.actualizarProducto(producto);
    }

    @Override
    public void eliminarProducto(Long id) {
        this.repositorioProducto.eliminarProducto(id);
    }

    @Override
    public List<Producto> listarProductos() throws NoHayProductos {
        if (repositorioProducto.listarProductos() != null) {
            return repositorioProducto.listarProductos();
        } else {
            throw new NoHayProductos();
        }
    }

    @Override
    public List<Producto> obtenerProductosPorEtapa(Long id) throws ProductoInexistente {
        if (repositorioProducto.obtenerProductosPorEtapa(id) != null) {
            return repositorioProducto.obtenerProductosPorEtapa(id);
        } else {
            throw new ProductoInexistente();
        }
    }

    @Override
    public Long consultarStockPorId(Long id) throws StockInexistente {
        if (repositorioProducto.consultarStockPorId(id) != 0){
            return repositorioProducto.consultarStockPorId(id);
        } else {
            throw new StockInexistente();
        }
    }


}
