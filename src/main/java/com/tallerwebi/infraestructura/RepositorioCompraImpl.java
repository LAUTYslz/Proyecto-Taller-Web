package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Compra;
import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioCompra;
import com.tallerwebi.dominio.excepcion.CompraInexistente;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository("repositorioCompra")
public class RepositorioCompraImpl implements RepositorioCompra {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioCompraImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Compra buscarCompraPorId(Long id){
        return (Compra) sessionFactory.getCurrentSession()
                .createCriteria(Compra.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public List<Compra> getAllCompras() {
        return (List<Compra>) sessionFactory.getCurrentSession()
                .createCriteria(Compra.class)
                .list();
    }

    @Override
    public void agregarCompra(Compra compra) {
        sessionFactory.getCurrentSession().save(compra);
    }

    @Override
    public void eliminarCompra(Compra compra) {
        sessionFactory.getCurrentSession().delete(compra);
    }

    @Override
    public void actualizarCompra(Compra compra) {
        sessionFactory.getCurrentSession().saveOrUpdate(compra);
    }
}
