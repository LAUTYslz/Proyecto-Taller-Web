package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorTienda {

    private final ServicioProducto servicioProducto;
    private final ServicioLogin servicioLogin;
    private final ServicioCompra servicioCompra;

    @Autowired
    public ControladorTienda(ServicioProducto servicioProducto, ServicioLogin servicioLogin, ServicioCompra servicioCompra) {
        this.servicioProducto = servicioProducto;
        this.servicioLogin = servicioLogin;
        this.servicioCompra = servicioCompra;
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
            model.addAttribute("mensaje", "Actualmente no contamos con productos para esta etapa");
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

    @RequestMapping("/finalizarCompra")
    public ModelAndView finalizarCompra(@RequestParam("totalCompra") Double totalCompra, HttpServletRequest request){
        ModelMap model = new ModelMap();
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        if (usuario == null){
            return new ModelAndView("redirect:/login");
        }

        Compra carrito = servicioCompra.getCarritoByUser(usuario);

        if (carrito == null){
            model.addAttribute("mensaje", "Lo sentimos. Algo falló al procesar la compra. Inténtalo nuevamente.");
            return new ModelAndView("carrito", model);
        } else {
            carrito.setTotal(totalCompra);
            servicioCompra.actualizarCompra(carrito);
            model.addAttribute("compra", carrito);

            DatosCompra datosCompra = new DatosCompra();
            datosCompra.setCompra(carrito);
            model.addAttribute("datosCompra", datosCompra);

        }

        return new ModelAndView ("formularioDePago", model);

    }

    @RequestMapping(path= "/completarPago", method=RequestMethod.POST)
    public ModelAndView completarPago(@ModelAttribute("datosCompra") DatosCompra datosCompra, HttpServletRequest request){
        ModelMap model = new ModelMap();
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        if (usuario == null){
            return new ModelAndView("redirect:/login");
        }

        Compra carrito = servicioCompra.getCarritoByUser(usuario);

        if (carrito == null){
            model.put("error", "Carrito no encontrado");
            return new ModelAndView("formularioDePago", model);
        }

        try {
            // Si la compra fue exitosa guardarla en el model y redirigir a compraExitosa.html
            servicioCompra.darDeAltaCompra(datosCompra, usuario);

            // Obtengo cada producto del carrito y disminuyo su stock, ya que la compra fue finalizada
            for (Producto producto : servicioCompra.getProductosDeCompra(carrito.getId())) {
                Long id = producto.getId();
                servicioProducto.disminuirStockDeProducto(id);
            }
            model.put("datosCompra", datosCompra);

        } catch (TarjetaInvalida e) {
            model.put("error", "El número de tarjeta ingresado no es correcto.");
            return new ModelAndView("formularioDePago", model);
        } catch (CodigoInvalido e) {
            model.put("error", "La tarjeta está vencida");
            return new ModelAndView("formularioDePago", model);
        } catch (ProductoInexistente e) {
            model.put("error", "Lo siento, hubo un error al procesar tu compra.");
            return new ModelAndView("formularioDePago", model);
        }

        return new ModelAndView("compraExitosa", model);
    }


}
