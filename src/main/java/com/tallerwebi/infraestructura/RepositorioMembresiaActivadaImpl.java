package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository("repositorioMembresiaActivada")
public class RepositorioMembresiaActivadaImpl implements RepositorioMembresiaActivada {
    private final SessionFactory sessionFactory;

    public RepositorioMembresiaActivadaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    @Override
    public Consulta guardarConsulta(Consulta consulta) {
        sessionFactory.getCurrentSession().save(consulta);
        return consulta;
    }

    @Override
    public void actualizarConsulta(Consulta consulta) {
        sessionFactory.getCurrentSession().update(consulta);
    }

    @Override
    public Consulta buscarConsulta(Long usuarioid) {
        return (Consulta) sessionFactory.getCurrentSession()
                .createCriteria(Consulta.class)
                .createAlias("usuario","usuarioBuscado")
                .add(Restrictions.eq("usuarioBuscado.id",usuarioid)).uniqueResult();

    }
}