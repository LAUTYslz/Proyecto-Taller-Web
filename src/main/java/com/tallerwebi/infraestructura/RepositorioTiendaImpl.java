package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioTienda;
import com.tallerwebi.dominio.Tienda;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository("repositorioTienda")
public class RepositorioTiendaImpl implements RepositorioTienda {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioTiendaImpl (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Tienda> obtenerListadoDeTiendas() {
        return (List<Tienda>) sessionFactory.getCurrentSession()
                .createCriteria(Tienda.class)
                .list();
    }

    @Override
    public Tienda obtenerTiendaPorId(Long id) {
        return (Tienda) sessionFactory.getCurrentSession()
                .createCriteria(Tienda.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public void guardarTienda(Tienda tienda) {
        sessionFactory.getCurrentSession().save(tienda);
    }

    @Override
    public void eliminarTienda(Long id) {
        sessionFactory.getCurrentSession().delete(obtenerTiendaPorId(id));
    }

    @Override
    public void actualizarTienda(Tienda tienda) {
        sessionFactory.getCurrentSession().saveOrUpdate(tienda);
    }
}
