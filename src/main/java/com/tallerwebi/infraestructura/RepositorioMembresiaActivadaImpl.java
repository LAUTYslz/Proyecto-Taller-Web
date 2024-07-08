package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

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
    public List<Consulta> buscarConsulta(Long usuarioid) {
        return (List<Consulta>) sessionFactory.getCurrentSession()
                .createCriteria(Consulta.class)
                .createAlias("usuario", "usuarioBuscado")
                .add(Restrictions.eq("usuarioBuscado.id", usuarioid)).list();

    }

    @Override
    public List<Consulta> buscarConsultasPorProfesionales(String email) {
        return (List<Consulta>) sessionFactory.getCurrentSession()
                .createCriteria(Consulta.class)
                .createAlias("profesional", "profesionalBuscado")
                .add(Restrictions.eq("profesionalBuscado.email", email)).list();

    }

    @Override
    public Consulta buscarConsultaPorId(Long consultaId) {
        return (Consulta) sessionFactory.getCurrentSession().createCriteria(Consulta.class)
                .add(Restrictions.eq("id", consultaId))
                .uniqueResult();

    }

    @Override
    public List<Consulta> todasLasConsultasCreadas() {
       return  (List<Consulta>) sessionFactory.getCurrentSession()
                .createCriteria(Consulta.class).list();
    }
}