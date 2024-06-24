package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.ProductoInexistente;
import com.tallerwebi.dominio.excepcion.StockInexistente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorTienda {
    private final RepositorioUsuario repositorioUsuario;
    private final ServicioTienda servicioTienda;
    private final ServicioProducto servicioProducto;
    private final RepositorioTienda repositorioTienda;
    private final RepositorioProducto repositorioProducto;

    public ControladorTienda(RepositorioUsuario repositorioUsuario, ServicioTienda servicioTienda, ServicioProducto servicioProducto, RepositorioTienda repositorioTienda, RepositorioProducto repositorioProducto) {
        this.repositorioUsuario = repositorioUsuario;
        this.servicioTienda = servicioTienda;
        this.servicioProducto = servicioProducto;
        this.repositorioTienda = repositorioTienda;
        this.repositorioProducto = repositorioProducto;
    }

    @RequestMapping("/productos/etapa/{etapa}")
    public ModelAndView mostrarProductosDeEtapa(@PathVariable Long etapa){
        ModelMap model = new ModelMap();

        try {
            List<Producto> productos = servicioProducto.obtenerProductosPorEtapa(etapa);
            model.addAttribute("productos", productos);
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

    @RequestMapping("/comprarAhora")
    public ModelAndView comprarAhora(){
        return new ModelAndView("comprarProducto");
    }

}
