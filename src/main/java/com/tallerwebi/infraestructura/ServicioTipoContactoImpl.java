package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioTipoProfesional;
import com.tallerwebi.dominio.ServicioTipoProfesional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioTipoContactoImpl implements ServicioTipoProfesional {
    private final RepositorioTipoProfesional repositorioTipoContacto;

    @Autowired
    public ServicioTipoContactoImpl(RepositorioTipoProfesional repositorioTipoContacto) {
    this.repositorioTipoContacto = repositorioTipoContacto;
    }

}
