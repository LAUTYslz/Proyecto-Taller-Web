package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorContacto {

    @RequestMapping(path = "/contacto", method = RequestMethod.GET)
    public ModelAndView irAContacto() {
        return new ModelAndView("contacto");
    }

    @GetMapping("/contacto/filtrarContactos")
    public ModelAndView mostrarContactos(@RequestParam(name = "categoria", required = false, defaultValue = "todos") String categoria) {
        ModelMap model = new ModelMap();

        // if (categoria.equals("todos")) {
        //     List<Contacto> contactos = servicioContactos.obtenerTodosLosContactos();
        // } else if (categoria.equals("tiendas")) {
        //     List<Contacto> tiendas = servicioContactos.obtenerContactosPorCategoria("tiendas");
        //     model.put("contactos", tiendas);
        // } else if (categoria.equals("pediatras")) {
        //     List<Contacto> pediatras = servicioContactos.obtenerContactosPorCategoria("pediatras");
        //     model.put("contactos", pediatras);
        // }
        return new ModelAndView("contactos", model);
    }

    @GetMapping("/contacto/tiendas")
    public ModelAndView mostrarTiendas() {
        ModelMap modelo = new ModelMap();
        //  ejemplo: List<Contacto> tiendas = servicioContactos.obtenerContactosPorCategoria("tiendas");
        // modelo.addObject("contactos", tiendas);
        return new ModelAndView ("contactos", modelo);
    }

    @GetMapping("/contacto/pediatras")
    public ModelAndView mostrarPediatras() {
        ModelMap modelo = new ModelMap();
        //  ejemplo: List<Contacto> pediatras = servicioContactos.obtenerContactosPorCategoria("pediatras");
        // modelo.addObject("contactos", pediatras);
        return new ModelAndView ("contactos", modelo);
    }
}
