package com.tallerwebi.Infraestructura;

import com.tallerwebi.dominio.RepositorioTipoContacto;
import com.tallerwebi.dominio.TipoContacto;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioTipoContactoImpl implements RepositorioTipoContacto {
    SessionFactory sessionFactory;

    @Autowired
    public RepositorioTipoContactoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public TipoContacto guardar(TipoContacto tipo) {
        sessionFactory.getCurrentSession().save(tipo);
        return tipo;
    }

    @Override
    public TipoContacto buscarPorNombre(String nombreTipo) {
        nombreTipo = nombreTipo.toUpperCase();
        return (TipoContacto) sessionFactory.getCurrentSession()
                .createCriteria(TipoContacto.class)
                .add(Restrictions.eq("nombre", nombreTipo))
                .uniqueResult();
    }

    @Override
    public List<TipoContacto> buscarTipos() {
        return sessionFactory.getCurrentSession().
                createCriteria(TipoContacto.class)
                .list();
    }
}
