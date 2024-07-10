package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
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
    private final RepositorioCompra repositorioCompra;

    @Autowired
    public ServicioProductoImpl(RepositorioProducto repositorioProducto, RepositorioCompra repositorioCompra) {
        this.repositorioProducto = repositorioProducto;
        this.repositorioCompra = repositorioCompra;
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
    public void eliminarProducto(Long idProducto) throws ProductoInexistente {
        List<Compra> compras = repositorioCompra.listarCompras();
        Producto producto = null;

        try {
            producto = buscarProductoPorId(idProducto);
        } catch (ProductoInexistente e){
            throw new ProductoInexistente();
        }

        if (compras != null) {
            for (Compra compra : compras) {
                compra.getProductos().remove(producto);
            }
        }

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

    @Override
    public void setearImagen(Long idProducto, String imagenUrl) throws ProductoInexistente {
        Producto producto = this.repositorioProducto.buscarProductoPorId(idProducto);

        if (producto != null){
            producto.setImagenUrl(imagenUrl);
            this.repositorioProducto.actualizarProducto(producto);
        } else {
            throw new ProductoInexistente();
        }
    }

}
