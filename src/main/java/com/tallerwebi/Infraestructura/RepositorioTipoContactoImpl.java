package com.tallerwebi.Infraestructura;

import com.tallerwebi.dominio.RepositorioTipoContacto;
import com.tallerwebi.dominio.TipoContacto;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioTipoContactoImpl implements RepositorioTipoContacto {
    SessionFactory sessionFactory;

    @Autowired
    public RepositorioTipoContactoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(TipoContacto tipo) {
        sessionFactory.getCurrentSession().save(tipo);
    }
}
