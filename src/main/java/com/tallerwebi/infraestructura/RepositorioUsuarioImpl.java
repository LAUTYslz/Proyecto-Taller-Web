package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Hijo;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;



    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Usuario buscarUsuario(String email, String password) {

        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }

    @Override
    public void guardar(Usuario usuario) {

        sessionFactory.getCurrentSession().save(usuario);

    }

    @Override
    public Usuario buscar(String email) {
        return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public void modificar(Usuario usuario) {

        sessionFactory.getCurrentSession().update(usuario);
    }


    @Override
    public Usuario buscarPorId(Long id) {
        return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public void guardarHijo(Hijo hijo) {
        sessionFactory.getCurrentSession().save(hijo);
    }

    @Override
    public void actualizar(Usuario usuario) {
    sessionFactory.getCurrentSession().update(usuario);
    }

    @Override
    public Usuario findByEmail(String userEmail) {
        return (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("email", userEmail))
                .uniqueResult();
    }

    @Override
    public List<Hijo> buscarHijosPorId(Long usuarioid){
        return (List<Hijo>) sessionFactory.getCurrentSession()
                .createCriteria(Hijo.class)
                .createAlias("usuario","usuarioBuscado")
                .add(Restrictions.eq("usuarioBuscado.id",usuarioid)).list();

    }

    @Override
    public void borrarHijo(Long hijoId) {
        Hijo hijo = sessionFactory.getCurrentSession().load(Hijo.class, hijoId);
        sessionFactory.getCurrentSession().delete(hijo);
    }

    @Override
    public void actualizarHijo(Hijo hijo) {
        sessionFactory.getCurrentSession().update(hijo);
    }
}




