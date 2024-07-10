package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PreguntaTest;
import com.tallerwebi.dominio.PreguntaTestRepositorio;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PreguntaTestRepositorioImpl implements PreguntaTestRepositorio {
    private final SessionFactory sessionFactory;

    @Autowired
    public PreguntaTestRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(PreguntaTest preguntaTest) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(preguntaTest);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List <PreguntaTest> obtenerTodos() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            List<PreguntaTest> result = session.createQuery("from PreguntaTest", PreguntaTest.class).list();
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
    public void editarPregunta(PreguntaTest preguntaTest) {
        guardar(preguntaTest);  // `saveOrUpdate` maneja tanto la creación como la actualización
    }

    @Override
    public void eliminarPorId(Long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            PreguntaTest preguntaTest = session.get(PreguntaTest.class, id);
            if (preguntaTest != null) {
                session.delete(preguntaTest);
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
    public Optional<PreguntaTest> findById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            PreguntaTest preguntaTest = session.get(PreguntaTest.class, id);
            session.getTransaction().commit();
            return Optional.ofNullable(preguntaTest);
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    @Override
    public List<PreguntaTest> obtenerPreguntasPorModeloTestId(Long modeloTestId) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            List<PreguntaTest> result = session.createQuery("from PreguntaTest where modeloTest.id = :modeloTestId", PreguntaTest.class)
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
