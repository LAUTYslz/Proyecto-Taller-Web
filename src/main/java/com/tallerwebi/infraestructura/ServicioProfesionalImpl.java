package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ServicioProfesionalImpl implements ServicioProfesional {
    private final RepositorioProfesional repositorioProfesional;
    private final RepositorioMetodo repositorioMetodo;
    private final RepositorioTipoProfesional repositorioTipoProfesional;
    private final RepositorioTurno repositorioTurno;
    private final RepositorioDiasAtencion repositorioDiasAtencion;

    @Autowired
    public ServicioProfesionalImpl(RepositorioProfesional repositorioProfesional, RepositorioMetodo repositorioMetodo, RepositorioTipoProfesional repositorioTipoProfesional, RepositorioTurno repositorioTurno, RepositorioDiasAtencion repositorioDiasAtencion) {
        this.repositorioProfesional = repositorioProfesional;
        this.repositorioMetodo = repositorioMetodo;
        this.repositorioTipoProfesional = repositorioTipoProfesional;
        this.repositorioTurno = repositorioTurno;
        this.repositorioDiasAtencion = repositorioDiasAtencion;
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


        // Eliminar duplicados utilizando un HashSet para mantener la unicidad
        Set<Profesional> profesionalesUnicos = new LinkedHashSet<>(profesionales);

        return new ArrayList<>(profesionalesUnicos); // Devuelve la lista de profesionales únicos
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
    public Integer calcularMontoACobrar(String mailProfesional) {
        Profesional profesional = repositorioProfesional.buscarProfesionalPorEmail(mailProfesional);
        List<Turno> turnos = repositorioTurno.obtenerTurnosRealizadosPorProfesional(profesional.getId());
        Integer total = 0;
        total = turnos.size() * profesional.getValorConsulta();

        return total;
    }

    @Override
    public List<TipoProfesional> traerTodosLosTiposSinTienda() {
        return repositorioTipoProfesional.buscarTiposSinTienda();
    }

    @Override
    public Profesional traerPorEmail(String profesionalMail) {
        return repositorioProfesional.buscarProfesionalPorMail(profesionalMail);
    }

    @Override
    @Transactional
    public void guardarDiasAtencion(Profesional profesional, DiasSemana diaSemana, LocalTime horaDesde, LocalTime horaHasta, int duracionSesiones) {

        profesional.setDiaAtencion(diaSemana);
        profesional.setHoraDesde(horaDesde);
        profesional.setHoraHasta(horaHasta);
        profesional.setDuracionSesiones(duracionSesiones);

        repositorioProfesional.modificar(profesional);
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

