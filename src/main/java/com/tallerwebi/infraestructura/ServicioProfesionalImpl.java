package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.MetodoNoEncontrado;
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
        TipoProfesional tipo = repositorioTipoProfesional.buscarPorNombreDeTipo(nombreTipoContacto);
        if (tipo == null) {
            throw new TipoProfesionalNoEncontrado(nombreTipoContacto);
        }
        profesional.setTipo(tipo);

        if(tipo.getNombre().equals("Tienda")){
            repositorioProfesional.guardar(profesional);
            return;
        }

        Metodo metodo = repositorioMetodo.buscarPorNombreDeMetodo(nombreMetodo);
        if (metodo == null) {
            throw new MetodoNoEncontrado(nombreMetodo);
        }
        profesional.setMetodo(metodo);

        repositorioProfesional.guardar(profesional);
    }

    @Override
    public void actualizarProfesional(Profesional profesionalExistente) {
        repositorioProfesional.modificar(profesionalExistente);
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
    public Profesional guardar(Profesional profesional) {
        repositorioProfesional.guardar(profesional);
        return profesional;
    }



}
