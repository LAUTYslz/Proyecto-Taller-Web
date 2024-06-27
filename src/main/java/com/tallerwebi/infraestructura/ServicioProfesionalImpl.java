package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.MetodoNoEncontrado;
import com.tallerwebi.dominio.excepcion.NoPudoGuardarseElProfesional;
import com.tallerwebi.dominio.excepcion.NoSeEncontraronProfesionalesEnLaBusqueda;
import com.tallerwebi.dominio.excepcion.TipoProfesionalNoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioProfesionalImpl implements ServicioProfesional {
    private final RepositorioProfesional repositorioProfesional;
    private final RepositorioMetodo repositorioMetodo;
    private final RepositorioTipoProfesional repositorioTipoProfesional;


    @Autowired
    public ServicioProfesionalImpl(RepositorioProfesional repositorioProfesional, RepositorioMetodo repositorioMetodo, RepositorioTipoProfesional repositorioTipoProfesional) {
        this.repositorioProfesional = repositorioProfesional;
        this.repositorioMetodo = repositorioMetodo;
        this.repositorioTipoProfesional = repositorioTipoProfesional;
    }

    @Override
    public List<Profesional> traerProfesionales() {
        return repositorioProfesional.traerProfesionales();
    }

    @Override
    public void guardarProfesional(Profesional profesional, String nombreMetodo, String nombreTipoContacto) {
        if (nombreMetodo != null && nombreMetodo.isEmpty()) {
            nombreMetodo = null;
        }
        if (nombreTipoContacto != null && nombreTipoContacto.isEmpty()) {
            nombreTipoContacto = null;
        }

        TipoProfesional tipo = repositorioTipoProfesional.buscarPorNombreDeTipo(nombreTipoContacto);
        if (tipo == null) {
            throw new TipoProfesionalNoEncontrado(nombreTipoContacto);
        }
        profesional.setTipo(tipo);

        if(tipo.getNombre().equals("Tienda")){
            profesional.setMetodo(null);
            repositorioProfesional.guardar(profesional);
            return;
        }

        Metodo metodo;
        if (nombreMetodo != null) {
            metodo = repositorioMetodo.buscarPorNombreDeMetodo(nombreMetodo);
        }else{
            metodo = null;
        }
        profesional.setMetodo(metodo);

        repositorioProfesional.guardar(profesional);
    }

    @Override
    public void actualizarProfesional(Profesional profesional, String nombreMetodo, String nombreTipoContacto) {
        if (nombreMetodo != null && nombreMetodo.isEmpty()) {
            nombreMetodo = null;
        }
        if (nombreTipoContacto != null && nombreTipoContacto.isEmpty()) {
            nombreTipoContacto = null;
        }

        TipoProfesional tipo = repositorioTipoProfesional.buscarPorNombreDeTipo(nombreTipoContacto);
        if (tipo == null) {
            throw new TipoProfesionalNoEncontrado(nombreTipoContacto);
        }
        profesional.setTipo(tipo);

        if(tipo.getNombre().equals("Tienda")){
            profesional.setMetodo(null);
            repositorioProfesional.modificar(profesional);
            return;
        }

        Metodo metodo;
        if (nombreMetodo != null) {
            metodo = repositorioMetodo.buscarPorNombreDeMetodo(nombreMetodo);
        }else{
            metodo = null;
        }
        profesional.setMetodo(metodo);

        repositorioProfesional.modificar(profesional);
    }

    @Override
    public void actualizar(Profesional profesional) {
        repositorioProfesional.modificar(profesional);
    }


    @Override
    public void eliminarProfesional(Profesional profesionalExistente) {
        repositorioProfesional.eliminar(profesionalExistente);
    }

    @Override
    public List<Profesional> traerProfesionalesPorMetodo(String nombreMetodo) {
        if (repositorioMetodo.buscarPorNombreDeMetodo(nombreMetodo) == null) {
            throw new MetodoNoEncontrado(nombreMetodo);
        }
        List<Profesional> profesionales = repositorioProfesional.traerProfesionalesPorMetodo(nombreMetodo);
        return profesionales != null ? profesionales : new ArrayList<>();
    }

    @Override
    public List<Profesional> traerProfesionalesPorTipo(String nombreTipo) {
        if (repositorioTipoProfesional.buscarPorNombreDeTipo(nombreTipo) == null) {
            throw new TipoProfesionalNoEncontrado(nombreTipo);
        }
        List<Profesional> profesionales = repositorioProfesional.traerProfesionalesPorTipo(nombreTipo);
        return profesionales != null ? profesionales : new ArrayList<Profesional>();
    }

    @Override
    public List<Profesional> traerProfesionalesPorTipoYMetodo(String nombreTipo, String nombreMetodo) {

        if (nombreMetodo != null && nombreMetodo.isEmpty()) {
            nombreMetodo = null;
        }
        if (nombreTipo != null && nombreTipo.isEmpty()) {
            nombreTipo = null;
        }


        if(nombreTipo != null){
            if (repositorioTipoProfesional.buscarPorNombreDeTipo(nombreTipo) == null) {
                throw new TipoProfesionalNoEncontrado(nombreTipo);
            }
        }
        if(nombreMetodo != null){
            if (repositorioMetodo.buscarPorNombreDeMetodo(nombreMetodo) == null) {
                throw new MetodoNoEncontrado(nombreMetodo);
            }
        }


        List<Profesional> profesionales;

        if ((nombreMetodo != null && !nombreMetodo.isEmpty()) || (nombreTipo != null && !nombreTipo.isEmpty())) {
            if (nombreMetodo == null && nombreTipo!=null){
                profesionales = repositorioProfesional.traerProfesionalesPorTipo(nombreTipo);
                if (profesionales.isEmpty()){
                    throw new NoSeEncontraronProfesionalesEnLaBusqueda();
                }
            }else if(nombreMetodo != null && nombreTipo==null){
                profesionales = repositorioProfesional.traerProfesionalesPorMetodo(nombreMetodo);
                if(profesionales.isEmpty()){
                    throw new NoSeEncontraronProfesionalesEnLaBusqueda();
                }
            } else{
                profesionales = repositorioProfesional.traerProfesionalesPorTipoYMetodo(nombreTipo, nombreMetodo);
                if (profesionales.isEmpty()){
                    throw new NoSeEncontraronProfesionalesEnLaBusqueda();
                }
            }
        } else{
            profesionales = repositorioProfesional.traerProfesionales();
            if(profesionales.isEmpty()){
                throw new NoSeEncontraronProfesionalesEnLaBusqueda();
            }
        }
        return profesionales;
    }

    @Override
    public List<Metodo> traerTodosLosMetodos() {
        return repositorioMetodo.buscarMetodos();
    }

    @Override
    public List<TipoProfesional> traerTodosLosTipos() {
        return repositorioTipoProfesional.buscarTipos();
    }

    @Override
    public Profesional obtenerPorId(Long id) {
        return repositorioProfesional.buscarProfesionalPorId(id);
    }

    @Override
    public List<Profesional> traerProfesionalesSinTienda() {
        return repositorioProfesional.traerPrefesionalesSinTienda();
    }

    @Override
    public Profesional guardar(Profesional profesional) {
        try{
            repositorioProfesional.guardar(profesional);
            return profesional;
        } catch (Exception e) {
            throw new NoPudoGuardarseElProfesional();
        }

    }



}

