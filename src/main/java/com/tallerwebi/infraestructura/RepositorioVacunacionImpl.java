package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioVacunacion;
import com.tallerwebi.dominio.Vacunacion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
@Transactional
public class RepositorioVacunacionImpl implements RepositorioVacunacion {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void guardar(Vacunacion vacunacion) {
        sessionFactory.getCurrentSession().saveOrUpdate(vacunacion);
    }

    @Override
    public List<Vacunacion> obtenerVacunacionesPorHijo(Long hijoId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Vacunacion v WHERE v.hijo.id = :hijoId", Vacunacion.class)
                .setParameter("hijoId", hijoId)
                .getResultList();
    }

    @Override
    public void eliminar(Vacunacion vacunacion) {
        sessionFactory.getCurrentSession().delete(vacunacion);
    }
}