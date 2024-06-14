package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioTipoProfesional;
import com.tallerwebi.dominio.ServicioTipoProfesional;
import com.tallerwebi.dominio.TipoProfesional;
import com.tallerwebi.dominio.excepcion.TipoNoEncontradoPorID;
import com.tallerwebi.dominio.excepcion.TipoProfesionalNoEncontrado;
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

    @Override
    public TipoProfesional buscarTipoPorId(Long tipoId) {

        TipoProfesional tipoBuscado = repositorioTipoContacto.traerTipoPorId(tipoId);
        if (tipoBuscado == null) {
            throw new TipoNoEncontradoPorID(tipoId);
        }
        return tipoBuscado;
    }
}
