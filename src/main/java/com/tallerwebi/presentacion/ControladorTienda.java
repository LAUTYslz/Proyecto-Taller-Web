package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorTienda {

    private final ServicioProducto servicioProducto;
    private final ServicioLogin servicioLogin;
    private final ServicioCompra servicioCompra;
    private final ServicioTarjeta servicioTarjeta;
    private final ServicioCorreo servicioCorreo;

    @Autowired
    public ControladorTienda(ServicioProducto servicioProducto, ServicioLogin servicioLogin, ServicioCompra servicioCompra, ServicioTarjeta servicioTarjeta, ServicioCorreo servicioCorreo) {
        this.servicioProducto = servicioProducto;
        this.servicioLogin = servicioLogin;
        this.servicioCompra = servicioCompra;
        this.servicioTarjeta = servicioTarjeta;
        this.servicioCorreo = servicioCorreo;
    }

    @RequestMapping("/productos")
    public ModelAndView mostrarProductos() {
        return new ModelAndView("tiendaVirtual");
    }

    @RequestMapping("/productos/etapa/{etapa}")
    public ModelAndView mostrarProductosDeEtapa(@PathVariable Long etapa) {
        ModelMap model = new ModelMap();

        try {
            List<Producto> productos = servicioProducto.obtenerProductosPorEtapa(etapa);
            model.addAttribute("productos", productos);
            model.addAttribute("compra", new Compra());
        } catch (NoHayProductos e) {
            model.addAttribute("mensaje", "Actualmente no contamos con productos para esta etapa");
        }

        return new ModelAndView("productos", model);

    }

    @RequestMapping("/detalles/{id}")
    public ModelAndView obtenerDetallesDelProducto(@PathVariable Long id) {
        ModelMap model = new ModelMap();

        try {
            Producto p = servicioProducto.buscarProductoPorId(id);
            Long stock = servicioProducto.consultarStockPorProducto(id);
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

        if (usuario == null){
            return new ModelAndView("redirect:/login");
        }

        Compra carrito = servicioCompra.obtenerCarritoPorUsuario(usuario);

        if (carrito == null){
            carrito = servicioCompra.iniciarCompra(usuario.getId());
            servicioCompra.agregarCompra(carrito);
        }

        try {
            List<Producto> productosEnCarrito = servicioCompra.obtenerProductosDeCompra(carrito.getId());
            model.addAttribute("productos", productosEnCarrito);

        } catch (NoHayProductos e) {
            model.addAttribute("mensaje", "No hay productos en el carrito");
        }

        model.addAttribute("compra", carrito);
        return new ModelAndView("carrito", model);

    }

    @RequestMapping("/agregarProducto")
    public ModelAndView agregarProducto(@RequestParam("productoId") Long productoId, HttpServletRequest request){
        ModelMap model = new ModelMap();
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        if (usuario == null){
            return new ModelAndView("redirect:/login");
        }

        Compra carrito = servicioCompra.obtenerCarritoPorUsuario(usuario);
        if (carrito == null){
            carrito = servicioCompra.iniciarCompra(usuario.getId());
            servicioCompra.agregarCompra(carrito);
        } else {

            try {
                Producto producto = servicioProducto.buscarProductoPorId(productoId);
                Long stock = servicioProducto.consultarStockPorProducto(productoId);

                servicioCompra.agregarProductoACompra(producto, carrito.getId());


            } catch (ProductoInexistente e) {
                model.addAttribute("mensaje", "Lo siento. No hemos encontrado el producto que buscabas");
                return new ModelAndView("carrito", model);
            } catch (StockInexistente e) {
                model.addAttribute("mensaje", "Lo siento. No contamos con suficiente stock de este producto");
                return new ModelAndView("carrito", model);
            } catch (CompraInexistente e) {
                model.addAttribute("mensaje", "Lo siento. Hubo un error al actualizar tu carrito");
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

        Compra carrito = servicioCompra.obtenerCarritoPorUsuario(usuario);
        if (carrito == null){
            model.addAttribute("mensaje", "Carrito no encontrado");
        } else {

            try {
                Producto producto = servicioProducto.buscarProductoPorId(productoId);
                servicioCompra.eliminarProductoACompra(producto, carrito.getId());

            } catch (ProductoInexistente | CompraInexistente e) {
                model.addAttribute("mensaje", "Hubo un error al eliminar el producto del carrito");
                return new ModelAndView("carrito", model);
            }

        }

        return new ModelAndView("redirect:/carrito");
    }


    @RequestMapping("/finalizarCompra")
    public ModelAndView finalizarCompra(@RequestParam("totalCompra") Double totalCompra, HttpServletRequest request){
        ModelMap model = new ModelMap();
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        if (usuario == null){
            return new ModelAndView("redirect:/login");
        }

        Compra carrito = servicioCompra.obtenerCarritoPorUsuario(usuario);

        if (carrito == null){
            model.addAttribute("mensaje", "Lo sentimos. Algo falló al procesar la compra");
            return new ModelAndView("carrito", model);
        } else {
            model.addAttribute("compra", carrito);
            model.addAttribute("tarjeta", new Tarjeta());
        }

        return new ModelAndView("formularioDePagoCompra", model);

    }

    @RequestMapping("/completarPago")
    public ModelAndView completarPago(@ModelAttribute("tarjeta") Tarjeta tarjeta, @RequestParam("idCompra") Long idCompra,
                                      @RequestParam("direccion") String direccion, HttpServletRequest request){

        ModelMap model = new ModelMap();
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        if (usuario == null){
            return new ModelAndView("redirect:/login");
        }

        Compra carrito = servicioCompra.obtenerCarritoPorUsuario(usuario);

        if (carrito == null){
            model.addAttribute("mensaje", "Lo sentimos. Algo falló al procesar la compra");
            return new ModelAndView("carrito", model);
        } else {
            try {

                servicioTarjeta.validarTarjeta(tarjeta);
                servicioTarjeta.guardarTarjeta(tarjeta);
                servicioCompra.asociarTarjetaACompra(idCompra, tarjeta);
                servicioCompra.asociarDireccionACompra(idCompra, direccion);
                servicioCompra.marcarComoRealizada(carrito.getId());

                carrito = servicioCompra.buscarCompra(idCompra);
                model.addAttribute("compra", carrito);

            } catch (CompraInexistente e) {
                model.addAttribute("error", "Lo sentimos. Algo falló al procesar el pago");
                return new ModelAndView("formularioDePagoCompra", model);
            } catch (TarjetaInvalida e) {
                model.addAttribute("error", "Revisa los datos ingresados de tu tarjeta");
                return new ModelAndView("formularioDePagoCompra", model);
            }
        }

        enviarCorreo(usuario.getEmail());

        return new ModelAndView("compraExitosa", model);
    }

    private void enviarCorreo(String email){
        String asunto = "Compra";
        String cuerpo = "¡Gracias por tu compra! Estaremos en contacto para mandarte el link de seguimiento de tu producto.";
        servicioCorreo.enviarCorreo(email, asunto, cuerpo);
    }

}

