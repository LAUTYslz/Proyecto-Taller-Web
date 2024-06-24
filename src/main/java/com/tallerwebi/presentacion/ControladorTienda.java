package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.CompraInexistente;
import com.tallerwebi.dominio.excepcion.ProductoInexistente;
import com.tallerwebi.dominio.excepcion.StockInexistente;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ControladorTienda(RepositorioUsuario repositorioUsuario, ServicioTienda servicioTienda, ServicioProducto servicioProducto, RepositorioTienda repositorioTienda, RepositorioProducto repositorioProducto, ServicioLogin servicioLogin, ServicioCompra servicioCompra, RepositorioCompra repositorioCompra) {
        this.repositorioUsuario = repositorioUsuario;
        this.servicioTienda = servicioTienda;
        this.servicioProducto = servicioProducto;
        this.repositorioTienda = repositorioTienda;
        this.repositorioProducto = repositorioProducto;
        this.servicioLogin = servicioLogin;
        this.servicioCompra = servicioCompra;
        this.repositorioCompra = repositorioCompra;
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

    @RequestMapping("/agregarProducto")
    public ModelAndView agregarProducto(@RequestParam("productoId") Long productoId, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);
        HttpSession session = request.getSession();

        if (usuario == null) {
            // Usuario no autenticado
            return new ModelAndView("redirect:/login");
        }

        Compra compra = (Compra) session.getAttribute("compra");
        if (compra == null) {
            // inicializar si no existe
            compra = new Compra();
            servicioCompra.agregarCompra(compra, usuario.getId());
            session.setAttribute("compra", compra);
        }

        try {
            Producto producto = servicioProducto.buscarProductoPorId(productoId);
            Long stock = servicioProducto.consultarStockPorId(productoId);
            this.servicioCompra.agregarProducto(producto, compra.getId());
            this.servicioCompra.actualizarCompra(compra);
            compra = servicioCompra.buscarCompraPorId(compra.getId());
            session.setAttribute("compra", compra);
            model.addAttribute("mensaje", "Producto añadido al carrito");

        } catch (ProductoInexistente e){
            model.addAttribute("mensaje", "Lo siento. Hubo un error al añadir el producto al carrito");
        } catch (StockInexistente e) {
            model.addAttribute("mensaje", "Lo siento. No contamos con suficiente stock de este producto");
        } catch (CompraInexistente e) {
            model.addAttribute("mensaje", "Lo siento. No cuentas con un carrito.");
        }

        return new ModelAndView("productos", model);
    }

    @RequestMapping("/carrito")
    public ModelAndView mostrarCarrito(HttpServletRequest request){
        ModelMap model = new ModelMap();
        Compra compra = servicioCompra.obtenerCompraActual(request);

        if (compra == null) {
            model.addAttribute("mensaje", "Tu carrito está vacío");
        } else {
            model.addAttribute("compra", compra);
        } return new ModelAndView("carrito", model);

    }

    @RequestMapping("/eliminarProducto")
    public ModelAndView eliminarProducto(@RequestParam("productoId") Long productoId, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);
        Compra compraActual = servicioCompra.obtenerCompraActual(request);

        try {
            Producto producto = servicioProducto.buscarProductoPorId(productoId);
            this.servicioCompra.eliminarProducto(producto, compraActual.getId());
            model.addAttribute("mensaje", "Producto eliminado del carrito");

        } catch (ProductoInexistente e){
            model.addAttribute("mensaje", "Lo siento. Hubo un error al añadir el producto al carrito");
            return new ModelAndView("productos", model);
        } return new ModelAndView("carrito", model);
    }

}
