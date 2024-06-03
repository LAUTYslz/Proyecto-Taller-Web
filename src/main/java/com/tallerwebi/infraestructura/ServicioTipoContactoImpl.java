package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioTipoContacto;
import com.tallerwebi.dominio.ServicioTipoContacto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioTipoContactoImpl implements ServicioTipoContacto {
    private final RepositorioTipoContacto repositorioTipoContacto;

    @Autowired
    public ServicioTipoContactoImpl(RepositorioTipoContacto repositorioTipoContacto) {
    this.repositorioTipoContacto = repositorioTipoContacto;
    }

}
