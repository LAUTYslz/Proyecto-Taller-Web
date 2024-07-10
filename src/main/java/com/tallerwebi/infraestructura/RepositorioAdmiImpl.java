package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Caja;
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

    @Override
    public List<Juego> listarjuegosEtapas(Long id) {
       return  sessionFactory.getCurrentSession().createCriteria(Juego.class)
               .createAlias("etapa","etapaBuscada")
               .add(Restrictions.eq("etapaBuscada.id",id)).list();
    }
    @Override
    public List<Etapa> listaDeEtapas() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Etapa.class)
                .list();
    }

    @Override
    public void eliminarEtapas(Etapa etapa) {
 sessionFactory.getCurrentSession().delete(etapa);
    }

    @Override
    public Juego buscarJuegoPorId(Long id) {
        return (Juego) sessionFactory.getCurrentSession().createCriteria(Juego.class).
                add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public void actualizarJuegos(Juego buscarJuego) {
        sessionFactory.getCurrentSession().update(buscarJuego);
    }

    @Override
    public void eliminarJuegos(Juego juego) {
        sessionFactory.getCurrentSession().delete(juego);
    }

    @Override
    public Caja obtenerCajaDeProyecto() {
        return (Caja) sessionFactory.getCurrentSession().
                createCriteria(Caja.class).uniqueResult();
    }

}
