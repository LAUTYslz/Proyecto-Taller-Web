package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ModeloTest;
import com.tallerwebi.dominio.ModeloTestRepositorio;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

@Repository
public class ModeloTestRepositorioImpl implements ModeloTestRepositorio {
    private final SessionFactory sessionFactory;

    @Autowired
    public ModeloTestRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(ModeloTest modeloTest) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(modeloTest);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<ModeloTest> obtenerTodos() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            List<ModeloTest> result = session.createQuery("from ModeloTest", ModeloTest.class).list();
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
    public Optional<ModeloTest> findById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            ModeloTest modeloTest = session.get(ModeloTest.class, id);
            session.getTransaction().commit();
            return Optional.ofNullable(modeloTest);
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void editarTest(ModeloTest modeloTest) {
        guardar(modeloTest);  // `saveOrUpdate` maneja tanto la creación como la actualización
    }

    @Override
    public void eliminarPorId(Long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            ModeloTest modeloTest = session.get(ModeloTest.class, id);
            if (modeloTest != null) {
                session.delete(modeloTest);
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
    public void save(ModeloTest modeloTest) {

    }

}
