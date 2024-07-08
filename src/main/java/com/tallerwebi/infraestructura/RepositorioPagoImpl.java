package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Pago;
import com.tallerwebi.dominio.RepositorioPago;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository("repositorioPago")
public class RepositorioPagoImpl implements RepositorioPago {
    private final SessionFactory sessionFactory;

    public RepositorioPagoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


}
