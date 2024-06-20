package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Consulta;
import com.tallerwebi.dominio.RepositorioMembresiaActivada;
import org.hibernate.SessionFactory;
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
        sessionFactory.getCurrentSession().saveOrUpdate(consulta);
        return consulta;
    }
}