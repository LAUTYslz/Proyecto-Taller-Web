package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Profesional;
import com.tallerwebi.dominio.RepositorioTurno;
import com.tallerwebi.dominio.Turno;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioTurnoImpl implements RepositorioTurno {
    SessionFactory sessionFactory;

    public RepositorioTurnoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Turno> buscarTurnosPorUsuario(Usuario usuario) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Turno.class)
                //.createAlias("usuario", "usuarioBuscado")
                .add(Restrictions.eq("usuario", usuario))
                .list();
    }

    @Override
    public List<Turno> buscarTurnosPorProfesional(Profesional profesional) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Turno.class)
                .add(Restrictions.eq("profesional", profesional))
                .list();
    }

    @Override
    public Turno guardarTurno(Turno turno) {
        sessionFactory.getCurrentSession().save(turno);
        return turno;
    }

    @Override
    public Turno buscarPorId(Long turnoId) {
        return (Turno) sessionFactory.getCurrentSession()
                .createCriteria(Turno.class)
                .add(Restrictions.eq("id", turnoId))
                .uniqueResult();
    }

    @Override
    public void actualizarTurno(Turno turno) {
        sessionFactory.getCurrentSession().update(turno);
    }

    @Override
    public void eliminar(Turno turno) {
        sessionFactory.getCurrentSession().delete(turno);
    }
}
