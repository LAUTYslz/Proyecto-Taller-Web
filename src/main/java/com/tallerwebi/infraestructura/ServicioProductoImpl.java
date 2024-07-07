package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioProducto;
import com.tallerwebi.dominio.ServicioProducto;
import com.tallerwebi.dominio.excepcion.NoHayProductos;
import com.tallerwebi.dominio.excepcion.ProductoInexistente;
import com.tallerwebi.dominio.excepcion.StockInexistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioProducto")
@Transactional
public class ServicioProductoImpl implements ServicioProducto {

    private final RepositorioProducto repositorioProducto;

    @Autowired
    public ServicioProductoImpl(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }

    @Override
    public void guardarProducto(Producto producto) {
        this.repositorioProducto.guardarProducto(producto);
    }

    @Override
    public void actualizarProducto(Producto producto) {
        this.repositorioProducto.actualizarProducto(producto);
    }

    @Override
    public void eliminarProducto(Long idProducto) {
        this.repositorioProducto.eliminarProducto(idProducto);
    }

    @Override
    public Producto buscarProductoPorId(Long idProducto) throws ProductoInexistente {

        if(this.repositorioProducto.buscarProductoPorId(idProducto) != null){
            return this.repositorioProducto.buscarProductoPorId(idProducto);
        } else {
            throw new ProductoInexistente();
        }

    }

    @Override
    public Long consultarStockPorProducto(Long idProducto) throws StockInexistente {

        if (this.repositorioProducto.consultarStockPorProducto(idProducto) > 0){
            return this.repositorioProducto.consultarStockPorProducto(idProducto);
        } else{
            throw new StockInexistente();
        }

    }

    @Override
    public void disminuirStockDeProducto(Long idProducto) {
        this.repositorioProducto.disminuirStockDeProducto(idProducto);
    }

    @Override
    public List<Producto> obtenerProductosPorEtapa(Long idEtapa) throws NoHayProductos {
        if (this.repositorioProducto.obtenerProductosPorEtapa(idEtapa) != null){
            return this.repositorioProducto.obtenerProductosPorEtapa(idEtapa);
        } else {
            throw new NoHayProductos();
        }
    }

    @Override
    public List<Producto> listarProductos() {
        return this.repositorioProducto.listarProductos();
    }

}
