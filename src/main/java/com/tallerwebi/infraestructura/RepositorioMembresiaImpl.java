package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Membresia;
import com.tallerwebi.dominio.RepositorioMembresia;
import com.tallerwebi.dominio.RepositorioUsuario;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository("repositorioMembresia")
public class RepositorioMembresiaImpl implements RepositorioMembresia {

    private SessionFactory sessionFactory;


    @Autowired
    public RepositorioMembresiaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void guardarMembresia(Membresia membresia) {
        sessionFactory.getCurrentSession().save(membresia);
    }
}

