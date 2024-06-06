package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Profesional;
import com.tallerwebi.dominio.RepositorioProfesional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RepositorioProfesionalImpl implements RepositorioProfesional {
    SessionFactory sessionFactory;

    @Autowired
    public RepositorioProfesionalImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Profesional buscarProfesional(String email, String nombre) {
        final Session session = sessionFactory.getCurrentSession();
        return (Profesional) session.createCriteria(Profesional.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("nombre", nombre))
                .uniqueResult();
    }

    @Override
    @Transactional
    public void guardar(Profesional profesional) {
        sessionFactory.getCurrentSession().save(profesional);
    }

    @Override
    public Profesional buscar(String email) {
        return (Profesional) sessionFactory.getCurrentSession().createCriteria(Profesional.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public void modificar(Profesional profesional) {
        sessionFactory.getCurrentSession().update(profesional);
    }

    @Override
    public void eliminar(Profesional profesional) {
        sessionFactory.getCurrentSession().delete(profesional);
    }

    @Override
    @Transactional
    public List<Profesional> traerProfesionales() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Profesional.class)
                .list();
    }

    @Override
    public List<Profesional> traerProfesionalesPorMetodo(String nombreMetodo) {
        nombreMetodo = nombreMetodo.toUpperCase();
        return sessionFactory.getCurrentSession()
                .createCriteria(Profesional.class)
                .createAlias("metodo", "metodoBuscado")
                .add(Restrictions.eq("metodoBuscado.nombre",nombreMetodo))
                .list();
    }

    @Override
    public List<Profesional> traerProfesionalesPorTipo(String nombreTipo) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Profesional.class)
                .createAlias("tipo", "tipoBuscado")
                .add(Restrictions.eq("tipoBuscado.nombre",nombreTipo))
                .list();
    }

    @Override
    public List<Profesional> traerProfesionalesPorTipoYMetodo(String nombreTipo, String nombreMetodo) {
        nombreMetodo = nombreMetodo.toUpperCase();
        return sessionFactory.getCurrentSession()
                .createCriteria(Profesional.class)
                .createAlias("metodo", "metodoBuscado")
                .createAlias("tipo", "tipoBuscado")
                .add(Restrictions.eq("metodoBuscado.nombre",nombreMetodo))
                .add(Restrictions.eq("tipoBuscado.nombre",nombreTipo))
                .list();
    }


}
