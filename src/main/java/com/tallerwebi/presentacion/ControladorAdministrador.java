package com.tallerwebi.presentacion;

import com.sun.xml.txw2.Document;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EtapaInexistente;
import com.tallerwebi.dominio.excepcion.ProductoInexistente;
import com.tallerwebi.dominio.excepcion.juegoInexistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Controller
public class ControladorAdministrador {


    private ServicioLogin servicioLogin;
    private ServicioAdmi servicioAdmi;
    private ServicioProfesional servicioProfesional;
    private ServicioMetodo servicioMetodo;
    private ServicioTipoProfesional servicioTipoProfesional;
    private ServicioMembresiaActivada servicioMembresiaActivada;
    private ServicioPago servicioPago;
    private ServicioProducto servicioProducto;
    private ServicioTienda servicioTienda;

    @Autowired
    public ControladorAdministrador(ServicioLogin servicioLogin, ServicioAdmi servicioAdmi, ServicioProfesional servicioProfesional, ServicioMembresiaActivada servicioMembresiaActivada, ServicioPago servicioPago) {
        this.servicioLogin = servicioLogin;
        this.servicioAdmi = servicioAdmi;
        this.servicioProfesional = servicioProfesional;
        this.servicioMetodo = servicioMetodo;
        this.servicioTipoProfesional = servicioTipoProfesional;
        this.servicioMembresiaActivada = servicioMembresiaActivada;
        this.servicioPago= servicioPago;
    }


    @GetMapping("/administrador")
    public ModelAndView mostrarBienvenido(Model model, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        // Obtener el usuario actual
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        // Agregar el usuario
        modelAndView.addObject("usuario", usuario);
        List<Etapa> etapas = servicioAdmi.listaDeEtapas();
        List<Juego> juegos = servicioAdmi.listasDeJuegos();

        // Agregar las listas al modelo para que estén disponibles en la vista
        model.addAttribute("etapas", etapas);
        model.addAttribute("juegos", juegos);

        // Establecer la vista
        modelAndView.setViewName("administrador");

        return modelAndView;
    }

    @PostMapping("/administrador")
    public ModelAndView mostrarBienvenidoPost(Model model, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        // Obtener el usuario actual
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);

        // Agregar el usuario
        modelAndView.addObject("usuario", usuario);
        List<Etapa> etapas = servicioAdmi.listaDeEtapas();
        List<Juego> juegos = servicioAdmi.listasDeJuegos();


        model.addAttribute("etapas", etapas);
        model.addAttribute("juegos", juegos);


        modelAndView.setViewName("administrador");

        return modelAndView;
    }

    @GetMapping("/cerrar-sesion")
    public String cerrarSesion(HttpServletRequest request, HttpSession session) {
        // Invalidar la sesión y redirigir al inicio
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/crearEtapa")
    public String mostrarFormularioCreacionEtapa(Model model) {
        model.addAttribute("etapa", new Etapa());
        return "crearEtapa";
    }

    @PostMapping("/guardar-etapa")
    public String crearEtapa(Etapa etapa) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("etapa", etapa);
        servicioAdmi.guardarEtapa(etapa);
        return "redirect:/crearJuego";
    }


    @GetMapping("/crearJuego")
    public ModelAndView mostrarFormularioCrearJuego(Model model) {
        ModelAndView modelAndView = new ModelAndView("crearJuego");
        model.addAttribute("juego", new Juego());
        List<Etapa> etapasCreadas = servicioAdmi.listaDeEtapas();
        modelAndView.addObject("etapasCreadas", etapasCreadas);
        return modelAndView;
    }


    @PostMapping("/guardar-juego")
    public String crearJuego(Juego juego, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute(juego);
        modelAndView.addObject("juego", juego);
        servicioAdmi.guardarJuego(juego);
        return "redirect:/administrador";

    }

    @PostMapping("/modificar-etapa/{id}")
    public String mostrarFormularioModificarEtapa(@PathVariable Long id, Model model) throws EtapaInexistente {

        Etapa etapaBuscada = servicioAdmi.buscarEtapa(id);
        model.addAttribute(etapaBuscada);
        return "guardar-etapa";
    }


    @PostMapping("/actualizar-etapa")
    public String modificarEtapa(@ModelAttribute Etapa etapa) throws EtapaInexistente {
        servicioAdmi.actualizarEtapa(etapa);

        return "redirect:/administrador";
    }

    @PostMapping("/ver-juego-etapa/{id}")
    public String verJuegosPorEtapa(@PathVariable Long id, Model model, HttpServletRequest request) throws EtapaInexistente {
        Etapa etapaBuscada = servicioAdmi.buscarEtapa(id);
        model.addAttribute("etapa", etapaBuscada);
        List<Juego> lista = servicioAdmi.listasDeJuegosPorEtapa(etapaBuscada.getId());
        model.addAttribute("juegos", lista);

        // Obtener el rol del usuario autenticado
        Usuario usuario = servicioLogin.obtenerUsuarioActual(request);
        String rol = usuario.getRol();


        if (rol.equals("USUARIO")) {
            return "verJuegoPorEtapaUsuario";
        } else {
            return "verJuegoPorEtapaAdmi";
        }
    }


    @GetMapping("/modificar-juego/{id}")
    public String mostarformJuego(@PathVariable Long id, Model model) throws EtapaInexistente, juegoInexistente {

        Juego juegoBuscado = servicioAdmi.buscarJuegoPorId(id);
        model.addAttribute(juegoBuscado);

        return "modificarJuego";
    }

    @PostMapping("/actualizar-juego/{id}")
    public String modificarJuego(@PathVariable Long id, Model model) throws EtapaInexistente, juegoInexistente, juegoInexistente {

        Juego juegoBuscado = servicioAdmi.buscarJuegoPorId(id);
        model.addAttribute(juegoBuscado);
        servicioAdmi.actualizarJuego(juegoBuscado);
        return "verJuegoPorEtapaAdmi";
    }


    @PostMapping("/eliminar-juego/{id}")
    public String eliminarJuego(@PathVariable Long id, Model model) throws EtapaInexistente, juegoInexistente {

        Juego juegoBuscado = servicioAdmi.buscarJuegoPorId(id);
        servicioAdmi.eliminarJuego(juegoBuscado);
        return "verJuegoPorEtapaAdmi";
    }

    @GetMapping("/verjuego")
    public String verJuegoPorEtapaAdmi() {

        return "verJuegoPorEtapaAdmi";
    }

    @GetMapping("/verJuegoPorEtapaUsuario")
    public String verJuegoPorEtapaUsuario() {

        return "verJuegoPorEtapaUsuario";
    }

    @GetMapping("/verjuegoModificado")
    public String verJuegoMoa() {

        return "modificarJuego";
    }


    //----------------------PROFESIONALES---------------------------------------
    @GetMapping("/admin/gestionarProfesionales")
    public ModelAndView verGestionarProfesionales(
            @RequestParam(value = "metodo", required = false) String nombreMetodo,
            @RequestParam(value = "tipo", required = false) String nombreTipo) {
        ModelAndView mav = new ModelAndView("gestionarProfesionales");
        try {
            List<Profesional> profesionales = obtenerProfesionalesPorMetodoYTipo(nombreTipo, nombreMetodo);

            mav.addObject("profesionales", profesionales);
            mav.addObject("metodos", servicioProfesional.traerTodosLosMetodos());
            mav.addObject("tipos", servicioProfesional.traerTodosLosTipos());
        } catch (Exception e) {
            mav.addObject("error", e.getMessage());

        }
        //List<Profesional> profesionales = servicioProfesional.traerProfesionales();
        //mav.addObject("profesionales", profesionales);
        return mav;
    }

    public List<Profesional> obtenerProfesionalesPorMetodoYTipo(String nombreTipo, String nombreMetodo) {
        return servicioProfesional.traerProfesionalesPorTipoYMetodo(nombreTipo, nombreMetodo);
    }

    @GetMapping("/admin/gestionarProfesionales/crear")
    public ModelAndView mostrarFormularioNuevo() {
        ModelAndView mav = new ModelAndView("formulario_crear_profesional");
        mav.addObject("profesional", new Profesional());
        List<Metodo> metodos = servicioProfesional.traerTodosLosMetodos();
        List<TipoProfesional> tipos = servicioProfesional.traerTodosLosTipos();
        mav.addObject("metodos", metodos);
        mav.addObject("tipos", tipos);
        return mav;
    }

    @PostMapping("/admin/gestionarProfesionales/guardar")
    public ModelAndView agregarProfesional(
            @RequestParam("nombre") String nombre,
            @RequestParam("telefono") String telefono,
            @RequestParam("email") String email,
            @RequestParam("direccion") String direccion,
            @RequestParam("institucion") String institucion,
            @RequestParam("tipo") String nombreTipo,
            @RequestParam("metodo") String nombreMetodo) {
        ModelAndView mav = new ModelAndView();
        try {
            //TipoProfesional tipo = servicioTipoProfesional.buscarTipoPorId(tipoId);
            //Metodo metodo = servicioMetodo.buscarMetodoPorId(metodoId);
            Profesional profesional = new Profesional();
            profesional.setNombre(nombre);
            profesional.setTelefono(telefono);
            profesional.setEmail(email);
            profesional.setDireccion(direccion);
            profesional.setInstitucion(institucion);

            Usuario usuarioProf = new Usuario();
            usuarioProf.setNombre(nombre);
            usuarioProf.setEmail(email);

            servicioProfesional.guardarProfesional(profesional, nombreMetodo, nombreTipo);
            servicioLogin.registrarUsuarioProfesional(usuarioProf);
            mav.setViewName("redirect:/admin/gestionarProfesionales");
        } catch (RuntimeException e) {
            mav.setViewName("formulario_crear_profesional");
            mav.addObject("error", e.getMessage());
            mav.addObject("profesional", new Profesional());
            List<Metodo> metodos = servicioProfesional.traerTodosLosMetodos();
            List<TipoProfesional> tipos = servicioProfesional.traerTodosLosTipos();
            mav.addObject("metodos", metodos);
            mav.addObject("tipos", tipos);
        }
        return mav;
    }

    @GetMapping("/admin/gestionarProfesionales/editar/{id}")
    public ModelAndView editarProfesional(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("formulario_editar_profesional");
        Profesional profesional = servicioProfesional.obtenerPorId(id);
        mav.addObject("profesional", profesional);
        List<Metodo> metodos = servicioProfesional.traerTodosLosMetodos();
        List<TipoProfesional> tipos = servicioProfesional.traerTodosLosTipos();
        mav.addObject("metodos", metodos);
        mav.addObject("tipos", tipos);
        return mav;
    }

    @PostMapping("/admin/gestionarProfesionales/actualizar")
    public ModelAndView actualizarProfesional(@RequestParam("id") Long id,
                                              @RequestParam("nombre") String nombre,
                                              @RequestParam("telefono") String telefono,
                                              @RequestParam("email") String email,
                                              @RequestParam("direccion") String direccion,
                                              @RequestParam("institucion") String institucion,
                                              @RequestParam("tipo") String nombreTipo,
                                              @RequestParam("metodo") String nombreMetodo) {
        ModelAndView mav = new ModelAndView();
        try {
            Profesional profesional = servicioProfesional.obtenerPorId(id);
            if (profesional == null) {
                throw new Exception("No se encontró el profesional con el ID proporcionado.");
            }
            //Metodo metodo = servicioMetodo.buscarMetodoPorNombre(nombreMetodo);
            //TipoProfesional tipoProfesional = servicioTipoProfesional.buscarTipoPorNombre(nombreTipo);

            profesional.setNombre(nombre);
            profesional.setTelefono(telefono);
            profesional.setEmail(email);
            profesional.setDireccion(direccion);
            profesional.setInstitucion(institucion);


            servicioProfesional.actualizarProfesional(profesional, nombreMetodo, nombreTipo);
            mav.setViewName("redirect:/admin/gestionarProfesionales");
        } catch (Exception e) {
            mav.setViewName("formulario_editar_profesional");
            Profesional profesional = servicioProfesional.obtenerPorId(id);
            mav.addObject("error", e.getMessage());
            mav.addObject("profesional", profesional);
            List<Metodo> metodos = servicioProfesional.traerTodosLosMetodos();
            List<TipoProfesional> tipos = servicioProfesional.traerTodosLosTipos();
            mav.addObject("metodos", metodos);
            mav.addObject("tipos", tipos);

            System.err.println("Error al actualizar profesional: " + e.getMessage());
        }
        return mav;
    }

    @GetMapping("/admin/gestionarProfesionales/eliminar/{id}")
    public String eliminarProfesional(@PathVariable Long id) {
        Profesional profesional = servicioProfesional.obtenerPorId(id);
        servicioProfesional.eliminarProfesional(profesional);
        return "redirect:/admin/gestionarProfesionales";
    }

    @GetMapping("/admin/gestionarProfesionales/consultas")
    public ModelAndView consultaProfesional() {
        ModelAndView mav = new ModelAndView("consulta_profesional_admi");
        List<Profesional> profesionales = servicioProfesional.traerProfesionales();
        mav.addObject("profesionales", profesionales);
        List<Consulta> consultas = servicioMembresiaActivada.listaDeConsultascreadas();
        List<Metodo> metodos = servicioProfesional.traerTodosLosMetodos();
        List<TipoProfesional> tipos = servicioProfesional.traerTodosLosTipos();
        mav.addObject("metodos", metodos);
        mav.addObject("tipos", tipos);
        mav.addObject("consultas", consultas);
        return mav;
    }

    @GetMapping("/admin/gestionarProfesionales/liquidar/{id}")
    public ModelAndView liquidarConsultasProfesional(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("liquidacion_profesional_admi");
        List<Profesional> profesionales = servicioProfesional.traerProfesionales();
        mav.addObject("profesionales", profesionales);
        Profesional profesional = servicioProfesional.obtenerPorId(id);
        List<Consulta> consultas= servicioMembresiaActivada.buscarConsultasPorProfesionales(profesional.getEmail());
        Integer importeTotal =servicioMembresiaActivada.obtenerImporteTotalDeConsultasPorMesPorProfesional(profesional, consultas);
        List<Metodo> metodos = servicioProfesional.traerTodosLosMetodos();
        List<TipoProfesional> tipos = servicioProfesional.traerTodosLosTipos();

        mav.addObject("metodos", metodos);
        mav.addObject("importeTotal", importeTotal);
        mav.addObject("tipos", tipos);
        mav.addObject("profesional", profesional);
        mav.addObject("consultas", consultas);
        return mav;
    }



    @GetMapping("/admin/gestionarProfesionales/pagar/{idConsulta}/{idProfesional}")
    public ModelAndView liquidarConsultasProfesional(@PathVariable Long idConsulta, @PathVariable Long idProfesional) {
        ModelAndView mav = new ModelAndView("pago_consulta_profesional");
        Profesional profesional = servicioProfesional.obtenerPorId(idProfesional);
        List<Consulta> consultas= servicioMembresiaActivada.buscarConsultasPorProfesionales(profesional.getEmail());
        Integer importeTotal =servicioMembresiaActivada.obtenerImporteTotalDeConsultasPorMesPorProfesional(profesional, consultas);
        List<Metodo> metodos = servicioProfesional.traerTodosLosMetodos();
        List<TipoProfesional> tipos = servicioProfesional.traerTodosLosTipos();
        Consulta consulta = servicioMembresiaActivada.obtenerConsultaPorId(idConsulta);
        Caja caja = servicioAdmi.obtenerCaja();


        mav.addObject("metodos", metodos);
        mav.addObject("importeTotal", importeTotal);
        mav.addObject("tipos", tipos);
        mav.addObject("profesional", profesional);
        mav.addObject("consultas", consultas);
        mav.addObject("consulta", consulta);
        mav.addObject("caja", caja);
        return mav;
    }


    @PostMapping("/admin/gestionarProfesionales/procesarPago/{profesionalId}/{consultaId}")
    public ModelAndView procesarPago(
            @PathVariable Long profesionalId,
            @PathVariable Long consultaId) {
        ModelAndView mav = new ModelAndView("pago_realizado"); // Vista para mostrar el resultado del pago
        Profesional profesional = servicioProfesional.obtenerPorId(profesionalId);
        Caja caja = servicioAdmi.obtenerCaja(); // Obtener la caja para actualizar el saldo
        Consulta consulta = servicioMembresiaActivada.obtenerConsultaPorId(consultaId);
        // Generar el pago y actualizar la caja

        Pago pago = servicioPago.generarPago(profesional, consulta, caja);

        // Agregar objetos al modelo para mostrar en la vista
        mav.addObject("profesional", profesional);
        mav.addObject("pago", pago);
        mav.addObject("caja", caja);

        return mav;
    }


    @GetMapping("/admin/gestionarProfesionales/verpagos")
    public ModelAndView verPago(){
        ModelAndView mav = new ModelAndView("pago_efectuados"); // Vista para mostrar el resultado del pago
        List<Profesional> profesionales = servicioProfesional.traerProfesionales();
        Caja caja = servicioAdmi.obtenerCaja(); // Obtener la caja para actualizar el saldo
        List<Consulta> consultas = servicioMembresiaActivada.listaDeConsultascreadas();
        // Generar el pago y actualizar la caja
        List<Pago> pagos = servicioPago.obtenerListaPagos();


        // Agregar objetos al modelo para mostrar en la vista
        mav.addObject("profesionales", profesionales);
        mav.addObject("consultas", consultas);
        mav.addObject("pagos", pagos);
        mav.addObject("caja", caja);

        return mav;
    }
    //----------------------GESTIÓN DE PRODUCTOS ---------------------------------------
    @RequestMapping("/admin/gestionarProductos")
    public ModelAndView mostrarProductos() {
        ModelAndView mav = new ModelAndView("gestionarProductos");

        List<Producto> productos = servicioProducto.listarProductos();

        mav.addObject("productos", productos);

        return mav;

    }

    // CREACIÓN

    @RequestMapping("/admin/crearProducto")
    public ModelAndView crearProducto(){
        ModelAndView mav = new ModelAndView("form_crearProducto");

        List<Tienda> tiendas = servicioTienda.obtenerListadoDeTiendas();
        List<Etapa> etapas = servicioAdmi.listaDeEtapas();

        mav.addObject("producto", new Producto());
        mav.addObject("tiendas", tiendas);
        mav.addObject("etapas", etapas);

        return mav;

    }

    @RequestMapping("/admin/guardarProducto")
    public ModelAndView guardarProducto(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") Double precio,
            @RequestParam("stock") Long stock,
            @RequestParam("tienda") Long idTienda,
            @RequestParam("etapa") Long idEtapa,
            RedirectAttributes ra,
            @RequestParam(value = "imagenUrl") MultipartFile multipartFile) throws EtapaInexistente {

        ModelMap model = new ModelMap();

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setTienda(servicioTienda.obtenerTiendaPorId(idTienda));
        producto.setEtapa(servicioAdmi.buscarEtapa(idEtapa));
        producto.setImagenUrl(fileName);
        servicioProducto.guardarProducto(producto);

        String uploadDir = "src/main/webapp/resources/core/img";

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)){
            try {
                Files.createDirectory(uploadPath);
                InputStream inputStream = multipartFile.getInputStream();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                model.addAttribute("Hubo un error al cargar la imágen, intenta nuevamente más tarde");
            }

        }

        ra.addFlashAttribute("mensaje", "El producto ha sido guardado correctamente");

        return new ModelAndView("redirect:/admin/gestionarProductos", model);

    }

    // EDICIÓN
    @RequestMapping("/admin/editarProducto/{id}")
    public ModelAndView editarProducto(@PathVariable Long id){
        ModelAndView mav = new ModelAndView("form_editarProducto");

        try {
            Producto producto = servicioProducto.buscarProductoPorId(id);
            List<Tienda> tiendas = servicioTienda.obtenerListadoDeTiendas();
            List<Etapa> etapas = servicioAdmi.listaDeEtapas();

            mav.addObject("producto", producto);
            mav.addObject("tiendas", tiendas);
            mav.addObject("etapas", etapas);

        } catch (ProductoInexistente e){
            mav.addObject("error", "Hubo un error al cargar el producto. Intenta nuevamente más tarde");
        }

        return mav;
    }

    @PostMapping("/admin/guardarCambiosProducto")
    public ModelAndView guardarCambiosProducto(@RequestParam("idProducto") Long id,
                                               @RequestParam("nombre") String nombre,
                                               @RequestParam("descripcion") String descripcion,
                                               @RequestParam("precio") Double precio,
                                               @RequestParam("stock") Long stock,
                                               @RequestParam("tienda") Long idTienda,
                                               @RequestParam("etapa") Long idEtapa){

        ModelAndView mav = new ModelAndView("redirect:/admin/gestionarProductos");

        try {
            Producto p = servicioProducto.buscarProductoPorId(id);
            p.setNombre(nombre);
            p.setDescripcion(descripcion);
            p.setPrecio(precio);
            p.setStock(stock);
            p.setTienda(servicioTienda.obtenerTiendaPorId(idTienda));
            p.setEtapa(servicioAdmi.buscarEtapa(idEtapa));
            servicioProducto.actualizarProducto(p);
        } catch (ProductoInexistente e){
            mav.addObject("error", "Lo sentimos. No hemos podido guardar los cambios del producto");
        } catch (EtapaInexistente e) {
            mav.addObject("error", "Lo sentimos. No hemos podido obtener los datos de la etapa");
        }

        return mav;
    }

    //    ELIMINACION
    @RequestMapping("/admin/eliminarProducto/{id}")
    public ModelAndView eliminarProducto(@PathVariable Long id){
        ModelAndView mav = new ModelAndView("redirect:/admin/gestionarProductos");

        try {
            Producto producto = servicioProducto.buscarProductoPorId(id);
            servicioProducto.eliminarProducto(id);
        } catch (ProductoInexistente e){
            mav.addObject("error", "Lo sentimos. Hubo un error al eliminar el producto. Intenta nuevamente más tarde");
        }

        return mav;
    }


}









