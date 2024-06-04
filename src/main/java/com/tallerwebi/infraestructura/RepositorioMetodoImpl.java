package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Metodo;
import com.tallerwebi.dominio.RepositorioMetodo;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioMetodoImpl implements RepositorioMetodo {
    SessionFactory sessionFactory;

    @Autowired
    public RepositorioMetodoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Metodo guardar(Metodo metodo) {
        sessionFactory.getCurrentSession().save(metodo);
        return metodo;
    }

    @Override
    public Metodo buscarPorNombreDeMetodo(String nombreMetodo) {
        nombreMetodo = nombreMetodo.toUpperCase();
        return (Metodo) sessionFactory.getCurrentSession()
                .createCriteria(Metodo.class)
                .add(Restrictions.eq("nombre", nombreMetodo))
                .uniqueResult();
    }

    @Override
    public List<Metodo> buscarMetodos() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Metodo.class)
                .list();
    }
}