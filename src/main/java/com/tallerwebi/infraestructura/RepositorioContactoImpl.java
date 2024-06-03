package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Contacto;
import com.tallerwebi.dominio.RepositorioContacto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RepositorioContactoImpl implements RepositorioContacto {
    SessionFactory sessionFactory;

    @Autowired
    public RepositorioContactoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Contacto buscarContacto(String email, String nombre) {
        final Session session = sessionFactory.getCurrentSession();
        return (Contacto) session.createCriteria(Contacto.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("nombre", nombre))
                .uniqueResult();
    }

    @Override
    @Transactional
    public void guardar(Contacto contacto) {
        sessionFactory.getCurrentSession().save(contacto);
    }

    @Override
    public Contacto buscar(String email) {
        return (Contacto) sessionFactory.getCurrentSession().createCriteria(Contacto.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public void modificar(Contacto contacto) {
        sessionFactory.getCurrentSession().update(contacto);
    }

    @Override
    public void eliminar(Contacto contacto) {
        sessionFactory.getCurrentSession().delete(contacto);
    }

    @Override
    @Transactional
    public List<Contacto> traerContactos() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Contacto.class)
                .list();
    }

    @Override
    public List<Contacto> traerContactosPorMetodo(String nombreMetodo) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Contacto.class)
                .createAlias("metodo", "metodoBuscado")
                .add(Restrictions.eq("metodoBuscado.nombre",nombreMetodo))
                .list();
    }

    @Override
    public List<Contacto> traerContactosPorTipo(String nombreTipo) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Contacto.class)
                .createAlias("tipo", "tipoBuscado")
                .add(Restrictions.eq("tipoBuscado.nombre",nombreTipo))
                .list();
    }

    @Override
    public List<Contacto> traerContactosPorTipoYMetodo(String nombreTipo, String nombreMetodo) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Contacto.class)
                .createAlias("metodo", "metodoBuscado")
                .createAlias("tipo", "tipoBuscado")
                .add(Restrictions.eq("metodoBuscado.nombre",nombreMetodo))
                .add(Restrictions.eq("tipoBuscado.nombre",nombreTipo))
                .list();
    }


}
