package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.ModeloTest;
import com.tallerwebi.dominio.RespuestaTest;
import com.tallerwebi.dominio.RespuestaTestRepositorio;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

@Repository
public class RespuestaTestRepositorioImpl implements RespuestaTestRepositorio {
    private final SessionFactory sessionFactory;

    @Autowired
    public RespuestaTestRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(RespuestaTest respuestaTest) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(respuestaTest);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<RespuestaTest> obtenerTodos() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            List<RespuestaTest> result = session.createQuery("from RespuestaTest", RespuestaTest.class).list();
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    @Override
    public void editarRespuesta(RespuestaTest respuestaTest) {
        guardar(respuestaTest);  // `saveOrUpdate` maneja tanto la creación como la actualización
    }

    @Override
    public void eliminarPorId(Long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            RespuestaTest respuestaTest = session.get(RespuestaTest.class, id);
            if (respuestaTest != null) {
                session.delete(respuestaTest);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Optional <RespuestaTest> findById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            RespuestaTest respuestaTest = session.get(RespuestaTest.class, id);
            session.getTransaction().commit();
            return Optional.ofNullable(respuestaTest);
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    @Override
    public List<RespuestaTest> obtenerRespuestasPorModeloTestId(Long modeloTestId) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            List<RespuestaTest> result = session.createQuery("from RespuestaTest where modeloTest.id = :modeloTestId", RespuestaTest.class)
                    .setParameter("modeloTestId", modeloTestId)
                    .list();
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

}