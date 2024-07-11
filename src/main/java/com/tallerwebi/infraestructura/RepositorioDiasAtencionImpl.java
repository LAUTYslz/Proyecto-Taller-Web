package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.DiasAtencion;
import com.tallerwebi.dominio.RepositorioDiasAtencion;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioDiasAtencionImpl implements RepositorioDiasAtencion {
    SessionFactory sessionFactory;

    public RepositorioDiasAtencionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<DiasAtencion> buscarTodos() {
        return sessionFactory.getCurrentSession()
                .createCriteria(DiasAtencion.class)
                .list();
    }

    @Override
    public void guardar(DiasAtencion diasAtencion) {
        sessionFactory.getCurrentSession().save(diasAtencion);
    }


    @Override
    public void eliminarPorProfesionalId(Long idProfesional) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "DELETE FROM DiasAtencion WHERE profesional.id = :profesionalId");
        query.setParameter("profesionalId", idProfesional);
        query.executeUpdate();
    }
}
