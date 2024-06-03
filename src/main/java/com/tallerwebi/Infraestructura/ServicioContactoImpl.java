package com.tallerwebi.Infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.MetodoNoEncontrado;
import com.tallerwebi.dominio.excepcion.TipoContactoNoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioContactoImpl implements ServicioContacto {
    private final RepositorioContacto repositorioContacto;
    private final RepositorioMetodo repositorioMetodo;
    private final RepositorioTipoContacto repositorioTipoContacto;


    @Autowired
    ServicioContactoImpl(RepositorioContacto repositorioContacto, RepositorioMetodo repositorioMetodo, RepositorioTipoContacto repositorioTipoContacto) {
        this.repositorioContacto = repositorioContacto;
        this.repositorioMetodo = repositorioMetodo;
        this.repositorioTipoContacto = repositorioTipoContacto;
    }

    @Override
    public List<Contacto> traerContactos() {
        return repositorioContacto.traerContactos();
    }

    @Override
    public void guardarContacto(Contacto contacto, String nombreMetodo, String nombreTipoContacto) {
        TipoContacto tipo = repositorioTipoContacto.buscarPorNombre(nombreTipoContacto);
        if (tipo == null) {
            throw new TipoContactoNoEncontrado(nombreTipoContacto);
        }
        contacto.setTipo(tipo);

        if(tipo.getNombre().equals("Tienda")){
            repositorioContacto.guardar(contacto);
            return;
        }

        Metodo metodo = repositorioMetodo.buscarPorNombre(nombreMetodo);
        if (metodo == null) {
            throw new MetodoNoEncontrado(nombreMetodo);
        }
        contacto.setMetodo(metodo);

        repositorioContacto.guardar(contacto);
    }

    @Override
    public void actualizarContacto(Contacto contactoExistente) {
        repositorioContacto.modificar(contactoExistente);
    }

    @Override
    public void eliminarContacto(Contacto contactoExistente) {
        repositorioContacto.eliminar(contactoExistente);
    }

    @Override
    public List<Contacto> traerContactosPorMetodo(String nombreMetodo) {
        if (repositorioMetodo.buscarPorNombre(nombreMetodo) == null) {
            throw new MetodoNoEncontrado(nombreMetodo);
        }
        List<Contacto> contactos = repositorioContacto.traerContactosPorMetodo(nombreMetodo);
        return contactos != null ? contactos : new ArrayList<>();
    }

    @Override
    public List<Contacto> traerContactosPorTipo(String nombreTipo) {
        if (repositorioTipoContacto.buscarPorNombre(nombreTipo) == null) {
            throw new TipoContactoNoEncontrado(nombreTipo);
        }
        List<Contacto> contactos = repositorioContacto.traerContactosPorTipo(nombreTipo);
        return contactos != null ? contactos : new ArrayList<Contacto>();
    }

    @Override
    public List<Contacto> traerContactosPorTipoYMetodo(String nombreTipo, String nombreMetodo) {
        if (repositorioTipoContacto.buscarPorNombre(nombreTipo) == null) {
            throw new TipoContactoNoEncontrado(nombreTipo);
        }
        if (repositorioMetodo.buscarPorNombre(nombreMetodo) == null) {
            throw new MetodoNoEncontrado(nombreMetodo);
        }
        List<Contacto> contactos = repositorioContacto.traerContactosPorTipoYMetodo(nombreTipo, nombreMetodo);
        return contactos != null ? contactos : new ArrayList<>();
    }

    @Override
    public Contacto guardar(Contacto contacto) {
        repositorioContacto.guardar(contacto);
        return contacto;
    }



}
