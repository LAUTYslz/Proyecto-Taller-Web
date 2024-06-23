package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioProducto;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository("repositorioMembresia")
public class RepositorioProductoImpl implements RepositorioProducto {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioProductoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Producto buscarProductoPorNombre(String nombre) {
      return (Producto) sessionFactory.getCurrentSession()
              .createCriteria(Producto.class)
              .add(Restrictions.eq("nombre", nombre))
              .uniqueResult();
    }

    @Override
    public void agregarProducto(Producto producto) {
        sessionFactory.getCurrentSession().save(producto);
    }

    @Override
    public void actualizarProducto(Producto producto) {
        sessionFactory.getCurrentSession().update(producto);
    }

    @Override
    public void eliminarProducto(Long id) {
        sessionFactory.getCurrentSession().delete(obtenerInfoProducto(id));
    }

    @Override
    public Producto obtenerInfoProducto(Long id) {
        return (Producto) sessionFactory.getCurrentSession()
                .createCriteria(Producto.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public List<Producto> listarProductos() {
        return (List<Producto>) sessionFactory.getCurrentSession()
                .createCriteria(Producto.class)
                .list();
    }

    @Override
    public List<Producto> obtenerProductosPorEtapa(Long id) {
        return (List<Producto>) sessionFactory.getCurrentSession()
                .createCriteria(Producto.class)
                .add(Restrictions.eq("etapa_id", id))
                .list();
    }

    @Override
    public Long consultarStockPorId(Long id) {
        return (Long) sessionFactory.getCurrentSession()
                .createCriteria(Producto.class)
                .setProjection(Projections.property("stock"))
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }
}
