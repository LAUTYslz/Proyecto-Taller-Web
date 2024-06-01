package com.tallerwebi.Infraestructura;

import com.tallerwebi.dominio.Metodo;
import com.tallerwebi.dominio.RepositorioMetodo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioMetodoImpl implements RepositorioMetodo {
    SessionFactory sessionFactory;

    @Autowired
    public RepositorioMetodoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Metodo metodo) {
        sessionFactory.getCurrentSession().save(metodo);
    }
}