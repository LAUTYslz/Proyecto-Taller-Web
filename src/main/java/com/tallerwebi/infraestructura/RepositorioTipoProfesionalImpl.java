package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Profesional;
import com.tallerwebi.dominio.RepositorioTipoProfesional;
import com.tallerwebi.dominio.TipoProfesional;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioTipoProfesionalImpl implements RepositorioTipoProfesional {
    SessionFactory sessionFactory;

    @Autowired
    public RepositorioTipoProfesionalImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public TipoProfesional guardar(TipoProfesional tipo) {
        sessionFactory.getCurrentSession().save(tipo);
        return tipo;
    }

    @Override
    public TipoProfesional buscarPorNombreDeTipo(String nombreTipo) {
        //nombreTipo = nombreTipo.toUpperCase();
        return (TipoProfesional) sessionFactory.getCurrentSession()
                .createCriteria(TipoProfesional.class)
                .add(Restrictions.eq("nombre", nombreTipo))
                .uniqueResult();
    }

    @Override
    public List<TipoProfesional> buscarTipos() {
        return sessionFactory.getCurrentSession().
                createCriteria(TipoProfesional.class)
                .list();
    }

    @Override
    public TipoProfesional traerTipoPorId(Long tipoId) {
        return (TipoProfesional) sessionFactory.getCurrentSession()
                .createCriteria(TipoProfesional.class)
                .add(Restrictions.eq("id", tipoId))
                .uniqueResult();
    }

    @Override
    public List<TipoProfesional> buscarTiposSinTienda() {
        return sessionFactory.getCurrentSession()
                .createCriteria(TipoProfesional.class)
                .add(Restrictions.ne("nombre", "Tienda"))
                .list();
    }

}
