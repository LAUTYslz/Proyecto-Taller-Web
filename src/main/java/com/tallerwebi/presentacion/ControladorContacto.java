package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Contacto;
import com.tallerwebi.dominio.ServicioContacto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorContacto {

    private final ServicioContacto servicioContacto;

    @Autowired
    public ControladorContacto(ServicioContacto servicioContacto){
        this.servicioContacto = servicioContacto;
    }

    /*
    @RequestMapping(path = "/contacto", method = RequestMethod.GET)
    public ModelAndView irAContacto() {
        return new ModelAndView("contacto");
    }
    */

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

    @GetMapping("/contacto/todos")
    public String listarContactos(Model model) {
        List<Contacto> contactos = servicioContacto.traerContactos();
        model.addAttribute("contactos", contactos);
        return "contacto";
    }

    @PostMapping("/contactos")
    public String agregarContacto(Contacto contacto) {
        servicioContacto.guardar(contacto);
        return "redirect:/contacto";
    }


    public List<Contacto> obtenerContactosPorMetodo(String nombreMetodo) {
        return servicioContacto.traerContactosPorMetodo(nombreMetodo);
    }

    public List<Contacto> obtenerContactosPorTipo(String nombreTipo) {
        return servicioContacto.traerContactosPorTipo(nombreTipo);
    }

    public List<Contacto> obtenerContactos() {
        return servicioContacto.traerContactos();
    }

    public List<Contacto> obtenerContactosPorMetodoYTipo(String nombreTipo, String nombreMetodo) {
        return servicioContacto.traerContactosPorTipoYMetodo(nombreTipo, nombreMetodo);
    }

    @GetMapping("/contacto")
    public ModelAndView mostrarPaginaDeContactos(
            @RequestParam(value = "metodo", required = false) String nombreMetodo,
            @RequestParam(value = "tipo", required = false) String nombreTipo)
    {
        ModelAndView mav = new ModelAndView("contacto");
        try {
            List<Contacto> contactos;

            if (nombreMetodo != null && nombreMetodo.isEmpty()) {
                nombreMetodo = null;
            }
            if (nombreTipo != null && nombreTipo.isEmpty()) {
                nombreTipo = null;
            }

            if ((nombreMetodo != null && !nombreMetodo.isEmpty()) || (nombreTipo != null && !nombreTipo.isEmpty())) {
                contactos = obtenerContactosPorMetodoYTipo(nombreTipo, nombreMetodo);
            } else{
                contactos = obtenerContactos();
            }

            mav.addObject("contactos", contactos);
            mav.addObject("metodos", servicioContacto.traerTodosLosMetodos());
            mav.addObject("tipos", servicioContacto.traerTodosLosTipo());
        } catch (Exception e) {
            mav.addObject("error", e.getMessage());

        }

        return mav;
    }


/*
    @GetMapping("/filtrar")
    public ModelAndView filtrarContactosPorMetodo(String nombreMetodo) {
        ModelAndView mav = new ModelAndView("contacto/filtrarPorMetodo");
        mav.addObject("ContactosFiltradosPorMetodo", servicioContacto.traerContactosPorMetodo(nombreMetodo));
        return mav;
    }

    @GetMapping("/filtrar")
    public ModelAndView filtrarContactosPorTipo(String nombreTipo) {
        ModelAndView mav = new ModelAndView("contacto/filtrarPorTipo");
        mav.addObject("ContactosFiltradosPorTipo", servicioContacto.traerContactosPorTipo(nombreTipo));
        return mav;
    }*/

    @GetMapping("/filtrar")
    public ModelAndView filtrarContactosPorMetodoYTipo(String nombreMetodo, String nombreTipo) {
        ModelAndView mav = new ModelAndView("/contacto/filtrarPorMetodoYTipo");
        mav.addObject("ContactosFiltradosPorTipoYMetodo", servicioContacto.traerContactosPorTipoYMetodo(nombreTipo, nombreMetodo));
        return mav;
    }
}
