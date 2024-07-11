package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Pago;
import com.tallerwebi.dominio.RepositorioPago;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository("repositorioPago")
public class RepositorioPagoImpl implements RepositorioPago {
    private final SessionFactory sessionFactory;

    public RepositorioPagoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void agregraPago(Pago pago) {
        sessionFactory.getCurrentSession().save(pago);
        }

    @Override
    public Pago obtenerPago(Long pagoId) {
        return (Pago) sessionFactory.getCurrentSession().get(Pago.class, pagoId);
    }

    @Override
    public List<Pago> obtenerListaPagos() {
        return  sessionFactory.getCurrentSession().createCriteria(Pago.class)
                .list();
    }

}
