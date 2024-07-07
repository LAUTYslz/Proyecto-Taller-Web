package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioProducto;
import com.tallerwebi.dominio.excepcion.StockInexistente;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository("repositorioProducto")
public class RepositorioProductoImpl implements RepositorioProducto {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioProductoImpl (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarProducto(Producto producto) {
        sessionFactory.getCurrentSession().save(producto);
    }

    @Override
    public void actualizarProducto(Producto producto) {
        sessionFactory.getCurrentSession().saveOrUpdate(producto);
    }

    @Override
    public void eliminarProducto(Long idProducto) {
        sessionFactory.getCurrentSession().delete(buscarProductoPorId(idProducto));
    }

    @Override
    public Producto buscarProductoPorId(Long idProducto) {
        return (Producto) sessionFactory.getCurrentSession()
                .createCriteria(Producto.class)
                .add(Restrictions.eq("id", idProducto))
                .uniqueResult();
    }

    @Override
    public Long consultarStockPorProducto(Long idProducto){
        return (Long) sessionFactory.getCurrentSession()
                .createCriteria(Producto.class)
                .setProjection(Projections.property("stock"))
                .add(Restrictions.eq("id", idProducto))
                .uniqueResult();
    }

    @Override
    public void disminuirStockDeProducto(Long idProducto) {
        Producto producto = buscarProductoPorId(idProducto);
        if (producto != null){
            producto.setStock(producto.getStock() - 1);
            actualizarProducto(producto);
        }
    }

    @Override
    public List<Producto> obtenerProductosPorEtapa(Long idEtapa) {
        return (List<Producto>) sessionFactory.getCurrentSession()
                .createCriteria(Producto.class)
                .createAlias("etapa", "e")
                .add(Restrictions.eq("e.id", idEtapa))
                .list();
    }

    @Override
    public List<Producto> listarProductos() {
        return (List<Producto>) sessionFactory.getCurrentSession()
                .createCriteria(Producto.class)
                .list();
    }

}
