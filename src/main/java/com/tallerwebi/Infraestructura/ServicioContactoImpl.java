package com.tallerwebi.Infraestructura;

import com.tallerwebi.dominio.Contacto;
import com.tallerwebi.dominio.RepositorioContacto;
import com.tallerwebi.dominio.ServicioContacto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioContactoImpl implements ServicioContacto {
    private final RepositorioContacto repositorioContacto;

    @Autowired
    ServicioContactoImpl(RepositorioContacto repositorioContacto) {
        this.repositorioContacto = repositorioContacto;
    }

    @Override
    public List<Contacto> traerContactos() {
        return repositorioContacto.traerContactos();
    }

    @Override
    public void guardar(Contacto contacto) {
        repositorioContacto.guardar(contacto);
    }



}
