package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.MetodoNoEncontrado;
import com.tallerwebi.dominio.excepcion.NoSeEcncontraronContactosEnLaBusqueda;
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
    public ServicioContactoImpl(RepositorioContacto repositorioContacto, RepositorioMetodo repositorioMetodo, RepositorioTipoContacto repositorioTipoContacto) {
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
        TipoContacto tipo = repositorioTipoContacto.buscarPorNombreDeTipo(nombreTipoContacto);
        if (tipo == null) {
            throw new TipoContactoNoEncontrado(nombreTipoContacto);
        }
        contacto.setTipo(tipo);

        if(tipo.getNombre().equals("Tienda")){
            repositorioContacto.guardar(contacto);
            return;
        }

        Metodo metodo = repositorioMetodo.buscarPorNombreDeMetodo(nombreMetodo);
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
        if (repositorioMetodo.buscarPorNombreDeMetodo(nombreMetodo) == null) {
            throw new MetodoNoEncontrado(nombreMetodo);
        }
        List<Contacto> contactos = repositorioContacto.traerContactosPorMetodo(nombreMetodo);
        return contactos != null ? contactos : new ArrayList<>();
    }

    @Override
    public List<Contacto> traerContactosPorTipo(String nombreTipo) {
        if (repositorioTipoContacto.buscarPorNombreDeTipo(nombreTipo) == null) {
            throw new TipoContactoNoEncontrado(nombreTipo);
        }
        List<Contacto> contactos = repositorioContacto.traerContactosPorTipo(nombreTipo);
        return contactos != null ? contactos : new ArrayList<Contacto>();
    }

    @Override
    public List<Contacto> traerContactosPorTipoYMetodo(String nombreTipo, String nombreMetodo) {
        if(nombreTipo != null){
            if (repositorioTipoContacto.buscarPorNombreDeTipo(nombreTipo) == null) {
                throw new TipoContactoNoEncontrado(nombreTipo);
            }
        }
        if(nombreMetodo != null){
            if (repositorioMetodo.buscarPorNombreDeMetodo(nombreMetodo) == null) {
                throw new MetodoNoEncontrado(nombreMetodo);
            }
        }


        List<Contacto> contactos;

        if(nombreMetodo == null && nombreTipo==null){
            contactos = repositorioContacto.traerContactos();
            if(contactos.isEmpty()){
                throw new NoSeEcncontraronContactosEnLaBusqueda();
            }
            return contactos != null ? contactos : new ArrayList<>();
        }

        if (nombreMetodo == null && nombreTipo!=null){
            contactos = repositorioContacto.traerContactosPorTipo(nombreTipo);
            if (contactos.isEmpty()){
                throw new NoSeEcncontraronContactosEnLaBusqueda();
            }
            return contactos != null ? contactos : new ArrayList<>();
        }

        if(nombreMetodo != null && nombreTipo==null){
            contactos = repositorioContacto.traerContactosPorMetodo(nombreMetodo);
            if(contactos.isEmpty()){
                throw new NoSeEcncontraronContactosEnLaBusqueda();
            }
            return contactos != null ? contactos : new ArrayList<>();
        }

        contactos = repositorioContacto.traerContactosPorTipoYMetodo(nombreTipo, nombreMetodo);
        if (contactos.isEmpty()){
            throw new NoSeEcncontraronContactosEnLaBusqueda();
        }
        return contactos;
    }

    @Override
    public List<Metodo> traerTodosLosMetodos() {
        return repositorioMetodo.buscarMetodos();
    }

    @Override
    public List<TipoContacto> traerTodosLosTipo() {
        return repositorioTipoContacto.buscarTipos();
    }

    @Override
    public Contacto guardar(Contacto contacto) {
        repositorioContacto.guardar(contacto);
        return contacto;
    }



}
