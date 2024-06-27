package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.CodigoInvalido;
import com.tallerwebi.dominio.excepcion.CompraInexistente;
import com.tallerwebi.dominio.excepcion.TarjetaInvalida;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("servicioCompra")
@Transactional
public class ServicioCompraImpl implements ServicioCompra {
    private final RepositorioCompra repositorioCompra;

    @Autowired
    public ServicioCompraImpl(RepositorioCompra repositorioCompra, RepositorioUsuario repositorioUsuario) {
        this.repositorioCompra = repositorioCompra;
    }

    @Override
    public Compra buscarCompraPorId(Long id) throws CompraInexistente {
        Compra compraBuscada = repositorioCompra.buscarCompraPorId(id);
        if (compraBuscada == null) {
            throw new CompraInexistente();
        } return compraBuscada;
    }

    @Override
    public List<Compra> getAllCompras() {
        return repositorioCompra.getAllCompras();
    }

    @Override
    public void agregarCompra(Compra compra) {
        repositorioCompra.agregarCompra(compra);
    }

    @Override
    @Transactional
    public Boolean agregarProducto(Producto producto, Long idCompra) {
        Compra compra = repositorioCompra.buscarCompraPorId(idCompra);
        if (compra == null){
            return false;
        } else {
            compra.agregarProducto(producto);
            repositorioCompra.actualizarCompra(compra);
        } return true;
    }

    @Override
    public Boolean eliminarProducto(Producto producto, Long idCompra) {
        Compra compra = repositorioCompra.buscarCompraPorId(idCompra);
        if (compra == null){
            return false;
        } else {
            compra.eliminarProducto(producto);
            repositorioCompra.actualizarCompra(compra);
        } return true;
    }

    @Override
    public Boolean finalizarCompra(Long id){
        Compra compra = repositorioCompra.buscarCompraPorId(id);
        if (compra == null) {
            return false;
        } else {
            compra.setEstado(EstadoCompra.REALIZADA);
            repositorioCompra.actualizarCompra(compra);
        } return true;
    }

    @Override
    public Boolean darDeAltaCompra(DatosCompra datosCompra, Usuario usuario) throws TarjetaInvalida, CodigoInvalido {
        if (!validarNumeroDeTarjeta(datosCompra.getTarjeta().getNumeroDeTarjeta())){
            throw new TarjetaInvalida();
        }

        if (!validarCVV(datosCompra.getTarjeta().getCodigoDeSeguridad())){
            throw new CodigoInvalido();
        }

        return finalizarCompra(datosCompra.getCompra().getId());
    }

    private Boolean validarCVV(Integer cvv) throws CodigoInvalido {

        if (cvv == null){
            throw new CodigoInvalido();
        }

        int longitud = cvv.toString().length();

        if (longitud != 3){
            throw new CodigoInvalido();
        }

        String cvvString = cvv.toString();

        for (char c : cvvString.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new CodigoInvalido();
            }
        } return true;
    }

    private Boolean validarNumeroDeTarjeta(Long numeroDeTarjeta) throws TarjetaInvalida {

        if (numeroDeTarjeta == null){
            throw new TarjetaInvalida();
        }


        int longitud = numeroDeTarjeta.toString().length();

        if (longitud < 15 || longitud > 16){
            throw new TarjetaInvalida();
        }

        String numeroDeTarjetaString = numeroDeTarjeta.toString();

        for (char c : numeroDeTarjetaString.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new TarjetaInvalida();
            }
        }

        return true;

    }

    @Override
    public Boolean cancelarCompra(Long id){
        Compra compra = repositorioCompra.buscarCompraPorId(id);
        if (compra == null) {
            return false;
        } else {
            compra.setEstado(EstadoCompra.CANCELADA);
            repositorioCompra.actualizarCompra(compra);
        } return true;
    }

    @Override
    public Boolean aplicarDescuento(Integer desc, Long idCompra) {
        Compra compra = repositorioCompra.buscarCompraPorId(idCompra);
        if (compra == null) {
            return false;
        } else {
            Double valorConDescuento = compra.getTotal() - ((compra.getTotal()*desc)/100);
            compra.setTotal(valorConDescuento);
            repositorioCompra.actualizarCompra(compra);
        } return true;
    }

    @Override
    public void actualizarCompra(Compra compra) {
        repositorioCompra.actualizarCompra(compra);
    }

    @Override
    public Compra getCarritoByUser(Usuario usuario) {
        return repositorioCompra.getCarritoByUser(usuario);
    }

    @Override
    public List<Producto> getProductosDeCompra(Long idCompra) {
        return repositorioCompra.getProductosDeCompra(idCompra);
    }


}
