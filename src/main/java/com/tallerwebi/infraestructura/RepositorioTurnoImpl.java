package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Date;
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

    @Override
    public List<Turno> traerTurnosActivosConProfesional(Long usuarioId, Long profesionalId) {
        List<EstadoTurno> estadosBuscados = Arrays.asList(EstadoTurno.PENDIENTE, EstadoTurno.CONFIRMADO);

        return sessionFactory.getCurrentSession()
                .createCriteria(Turno.class)
                .add(Restrictions.eq("usuario.id", usuarioId))
                .add(Restrictions.eq("profesional.id", profesionalId))
                .add(Restrictions.in("estado", estadosBuscados))
                .list();
    }

    @Override
    public List<Turno> traerTurnosActivosEnHorario(Long usuarioId, Date fechaHora) {
        List<EstadoTurno> estadosBuscados = Arrays.asList(EstadoTurno.PENDIENTE, EstadoTurno.CONFIRMADO);

        return sessionFactory.getCurrentSession()
                .createCriteria(Turno.class)
                .add(Restrictions.eq("usuario.id", usuarioId))
                .add(Restrictions.in("estado", estadosBuscados))
                .add(Restrictions.eq("fechaHora", fechaHora))
                .list();
    }

    @Override
    public boolean profesionalTieneTurnoEnFechaHora(Long profesionalId, Date fechaHora) {
        List<EstadoTurno> estadosBuscados = List.of(EstadoTurno.PENDIENTE, EstadoTurno.CONFIRMADO);
        return sessionFactory.getCurrentSession()
                    .createCriteria(Turno.class)
                    .add(Restrictions.eq("profesional.id", profesionalId))
                    .add(Restrictions.eq("fechaHora", fechaHora))
                    .add(Restrictions.in("estado", estadosBuscados))
                .uniqueResult() != null;
            //Este método retorna true si el profesional ya tiene un turno en la fecha y hora especificadas.
    }

    @Override
    public List<Turno> obtenerTurnosRealizadosPorProfesional(Long profesionalId) {
        List<EstadoTurno> estadoBuscado = List.of(EstadoTurno.REALIZADO);
        return sessionFactory.getCurrentSession()
                .createCriteria(Turno.class)
                .add(Restrictions.eq("profesional.id", profesionalId))
                .add(Restrictions.in("estado", estadoBuscado))
                .list();
    }

}



