package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Etapa;
import com.tallerwebi.dominio.Juego;
import com.tallerwebi.dominio.RepositorioAdmi;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository("repositorioAdmi")
public class RepositorioAdmiImpl implements RepositorioAdmi {
    SessionFactory sessionFactory;

    public RepositorioAdmiImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Etapa> listaDeEtapas() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Etapa.class)
                .list();
    }

    public void guardarEtapa(Etapa etapa) {
        sessionFactory.getCurrentSession().save(etapa);
    }

    @Override
    public void guardarJuego(Juego juego) {
        sessionFactory.getCurrentSession().save(juego);
    }

    @Override
    public List<Juego> listaDeJuegos() {
        return sessionFactory.getCurrentSession().createCriteria(Juego.class).list();
    }

    @Override
    public Etapa buscarEtapaPorId(Long id) {
        return (Etapa) sessionFactory.getCurrentSession().createCriteria(Etapa.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();

    }



    @Override
    public void actualizarEtapas(Etapa etapa) {
        sessionFactory.getCurrentSession().update(etapa);
        }

}