package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.DatosMembresia;
import com.tallerwebi.dominio.RepositorioMembresia;
import com.tallerwebi.dominio.Tarjeta;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository("repositorioMembresia")
public class RepositorioMembresiaImpl implements RepositorioMembresia {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioMembresiaImpl (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public DatosMembresia buscarMembresia(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (DatosMembresia) session.createCriteria(DatosMembresia.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public void registrarMembresia(DatosMembresia membresia) {
        sessionFactory.getCurrentSession().save(membresia);
    }

    @Override
    public void eliminarMembresia(Long id) {

        sessionFactory.getCurrentSession().delete(id);
    }

    @Override
    public List<DatosMembresia> listarMembresias() {
        return (List<DatosMembresia>) sessionFactory.getCurrentSession()
                .createCriteria(DatosMembresia.class)
                .list();
    }

    @Override
    public void guardarTarjeta(Tarjeta tarjeta) {
        sessionFactory.getCurrentSession().save(tarjeta);
    }
}
