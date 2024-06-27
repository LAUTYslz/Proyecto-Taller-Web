package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CodigoInvalido;
import com.tallerwebi.dominio.excepcion.CompraInexistente;
import com.tallerwebi.dominio.excepcion.TarjetaInvalida;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ServicioCompra {

   Compra buscarCompraPorId(Long id) throws CompraInexistente;
   List<Compra> getAllCompras();
   void agregarCompra(Compra compra);
   void eliminarCompra(Compra compra);
   Boolean agregarProducto(Producto producto, Long idCompra);
   Boolean eliminarProducto(Producto producto, Long idCompra);
   Boolean setearListaDeProductos(List<Producto> productos, Long idCompra);
   Boolean finalizarCompra(Long id);
   Boolean cancelarCompra(Long id);
   Boolean aplicarDescuento(Integer desc, Long idCompra);
   Compra obtenerCompraActual(HttpServletRequest request);
   void actualizarCompra(Compra compra);
   Compra getCarritoByUser(Usuario usuario);
   List<Producto> getProductosDeCompra(Long idCompra);
   Boolean darDeAltaCompra(DatosCompra datosCompra, Usuario usuario) throws TarjetaInvalida, CodigoInvalido;

}
