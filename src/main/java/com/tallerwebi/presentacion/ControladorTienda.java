package com.tallerwebi.presentacion;

import com.mysql.cj.AppendingBatchVisitor;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.CompraInexistente;
import com.tallerwebi.dominio.excepcion.ProductoInexistente;
import com.tallerwebi.dominio.excepcion.StockInexistente;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorTienda {
    private final RepositorioUsuario repositorioUsuario;
    private final ServicioTienda servicioTienda;
    private final ServicioProducto servicioProducto;
    private final RepositorioTienda repositorioTienda;
    private final RepositorioProducto repositorioProducto;
    private final ServicioLogin servicioLogin;
    private final ServicioCompra servicioCompra;
    private final RepositorioCompra repositorioCompra;
    private final SessionFactory sessionFactory;

    @Autowired
    public ControladorTienda(RepositorioUsuario repositorioUsuario, ServicioTienda servicioTienda, ServicioProducto servicioProducto, RepositorioTienda repositorioTienda, RepositorioProducto repositorioProducto, ServicioLogin servicioLogin, ServicioCompra servicioCompra, RepositorioCompra repositorioCompra, SessionFactory sessionFactory) {
        this.repositorioUsuario = repositorioUsuario;
        this.servicioTienda = servicioTienda;
        this.servicioProducto = servicioProducto;
        this.repositorioTienda = repositorioTienda;
        this.repositorioProducto = repositorioProducto;
        this.servicioLogin = servicioLogin;
        this.servicioCompra = servicioCompra;
        this.repositorioCompra = repositorioCompra;
        this.sessionFactory = sessionFactory;
    }

    @RequestMapping("/productos")
    public ModelAndView mostrarProductos() {
        return new ModelAndView("tiendaVirtual");
    }

    @RequestMapping("/productos/etapa/{etapa}")
    public ModelAndView mostrarProductosDeEtapa(@PathVariable Long etapa){
        ModelMap model = new ModelMap();

        try {
            List<Producto> productos = servicioProducto.obtenerProductosPorEtapa(etapa);
            model.addAttribute("productos", productos);
            model.addAttribute("compra", new Compra()); // Al ingresar en una etapa, se manda un objeto compra para ser llenado cada vez q el usuario aprieta 'Agregar al carrito'
        } catch (ProductoInexistente e){
            model.addAttribute("mensaje", "Actualmente no contamos con productos para la etapa de 0 a 3 años");
        }

        return new ModelAndView("productos", model);

    }

    @RequestMapping("/detalles/{id}")
    public ModelAndView obtenerDetallesDelProducto(@PathVariable Long id){
        ModelMap model = new ModelMap();

        try {
            Producto p = servicioProducto.buscarProductoPorId(id);
            Long stock = servicioProducto.consultarStockPorId(id);
            model.addAttribute("producto", p);
            model.addAttribute("stock", stock);
        } catch (ProductoInexistente e) {
            model.addAttribute("mensaje", "Lo siento, no hemos encontrado el producto que buscabas");
        } catch (StockInexistente e) {
            model.addAttribute("mensaje", "Lo siento, no contamos con suficiente stock de este producto");
        }
        return new ModelAndView("detallesProducto", model);
    }

    @RequestMapping("/carrito")
    public ModelAndView verCarrito(HttpServletRequest request){
        ModelMap model = new ModelMap();
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);
        Compra carrito = servicioCompra.getCarritoByUser(usuario);

        if (usuario == null){
            return new ModelAndView("redirect:/login");
        } else {
            carrito = servicioCompra.getCarritoByUser(usuario);
        }

        if (carrito == null){
            // Si el usuario no tiene carrito, crear uno y guardarlo en el repo:
            carrito = new Compra();
            carrito.setUsuario(usuario);
            carrito.setProductos(new ArrayList<Producto>());
            servicioCompra.agregarCompra(carrito);
        } else {
            List<Producto> productosEnCarrito = carrito.getProductos();

            // Calcular el valor de la compra
            Double totalCarrito = 0.0;
            for (Producto producto : productosEnCarrito){
                totalCarrito += producto.getPrecio();
            }
            // setear el total en el carrito
            carrito.setTotal(totalCarrito);
            // actualizar los valores
            servicioCompra.agregarCompra(carrito);
        }

        model.addAttribute("compra", carrito);
        return new ModelAndView("carrito", model);
    }

    @RequestMapping("/agregarProducto")
    public ModelAndView agregarProducto(@RequestParam("productoId") Long productoId, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);
        Compra carrito = servicioCompra.getCarritoByUser(usuario);

        if (usuario == null){
            return new ModelAndView("redirect:/login");
        }

        // Si es nulo, crearlo
        if (carrito == null){
            carrito = new Compra();
            carrito.setUsuario(usuario);
            carrito.setProductos(new ArrayList<Producto>());
            servicioCompra.agregarCompra(carrito);
        } else {
            // recuperar el producto y agregarlo al carrito
            try {
                Producto producto = servicioProducto.buscarProductoPorId(productoId);
                Long stock = servicioProducto.consultarStockPorId(productoId);
                // Agregar
                servicioCompra.agregarProducto(producto, carrito.getId());
                // Calcular total
                double totalCarrito = 0.0;
                for (Producto productoEnCarrito : carrito.getProductos()) {
                    totalCarrito += productoEnCarrito.getPrecio();
                }
                carrito.setTotal(totalCarrito);

                // guardar el carrito actualizado
                servicioCompra.actualizarCompra(carrito);

            } catch (ProductoInexistente e){
                model.addAttribute("mensaje", "El producto que intentas agregar ya no existe");
                return new ModelAndView("carrito", model);
            } catch (StockInexistente e) {
                model.addAttribute("mensaje", "No contamos con suficiente stock de este producto");
                return new ModelAndView("carrito", model);
            }
        }
        return new ModelAndView("redirect:/productos");
    }

    @RequestMapping("/eliminarProducto")
    public ModelAndView eliminarProducto(@RequestParam("productoId") Long productoId, HttpServletRequest request){
        ModelMap model = new ModelMap();
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        if (usuario == null){
            return new ModelAndView("redirect:/login");
        }

        Compra carrito = servicioCompra.getCarritoByUser(usuario);
        if (carrito == null){
            model.addAttribute("mensaje", "Carrito no encontrado");
        } else {
            try {
                Producto producto = servicioProducto.buscarProductoPorId(productoId);
                servicioCompra.eliminarProducto(producto, carrito.getId()); // elimino el producto y la entidad se encarga de restarlo del total
                servicioCompra.actualizarCompra(carrito); // actualizo la compra

            } catch (Exception e) {
                model.addAttribute("mensaje", "Hubo un error al eliminar el producto del carrito");
                return new ModelAndView("carrito", model);
            }
        } return new ModelAndView("redirect:/carrito");

    }



}
