package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Compra;
import com.tallerwebi.dominio.EstadoCompra;
import com.tallerwebi.dominio.RepositorioCompra;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository("repositorioCompra")
public class RepositorioCompraImpl implements RepositorioCompra {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioCompraImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Compra buscarCompra(Long id) {
        return (Compra) sessionFactory.getCurrentSession()
                .createCriteria(Compra.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public void agregarCompra(Compra compra) {
        if (buscarCompra(compra.getId()) == null) {
            sessionFactory.getCurrentSession().save(compra);
        } else {
            sessionFactory.getCurrentSession().merge(compra);
        }

    }

    @Override
    public void actualizarCompra(Compra compra) {
        if (buscarCompra(compra.getId()) == null) {
            sessionFactory.getCurrentSession().save(compra);
        } else {
            sessionFactory.getCurrentSession().merge(compra);
        }
    }

    @Override
    public void eliminarCompra(Compra compra) {
        sessionFactory.getCurrentSession().delete(compra);
    }

    @Override
    public List<Compra> listarCompras() {
        return (List<Compra>) sessionFactory.getCurrentSession()
                .createCriteria(Compra.class)
                .list();
    }

    @Override
    public List<Compra> obtenerHistorialDeComprasPorUsuario(Usuario usuario) {
        return (List<Compra>) sessionFactory.getCurrentSession()
                .createCriteria(Compra.class)
                .add(Restrictions.eq("usuario", usuario))
                .add(Restrictions.eq("estado", EstadoCompra.REALIZADA))
                .list();
    }

    @Override
    public Compra obtenerCarritoPorUsuario(Usuario usuario) {
        return (Compra) sessionFactory.getCurrentSession()
                .createCriteria(Compra.class)
                .add(Restrictions.eq("usuario", usuario))
                .add(Restrictions.eq("estado", EstadoCompra.PENDIENTE))
                .uniqueResult();

    }
}
