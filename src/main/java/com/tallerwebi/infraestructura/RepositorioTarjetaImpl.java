package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioTarjeta;
import com.tallerwebi.dominio.Tarjeta;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository("repositorioTarjeta")
public class RepositorioTarjetaImpl implements RepositorioTarjeta {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioTarjetaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void guardarTarjeta(Tarjeta tarjeta) {
        sessionFactory.getCurrentSession().save(tarjeta);
    }

    @Override
    public Tarjeta obtenerTarjetaPorId(Long id) {
        return (Tarjeta) sessionFactory.getCurrentSession()
                .createCriteria(Tarjeta.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

}
