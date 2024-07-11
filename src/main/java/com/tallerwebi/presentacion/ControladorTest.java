package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PreguntaTest;
import com.tallerwebi.dominio.RespuestaTest;
import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;


@Controller
public class ControladorTest {

    private final ServicioTest servicioTest;
    private final ServicioLogin servicioLogin;

    @Autowired
    public ControladorTest(ServicioTest servicioTest, ServicioLogin servicioLogin) {

        this.servicioTest = servicioTest;
        this.servicioLogin = servicioLogin;
    }


    @GetMapping("/seleccionTests")
    public String mostrarTests(Model model) {
        List<ModeloTest> modeloTest = servicioTest.obtenerTodosLosTests();
        model.addAttribute("modeloTest", modeloTest);
        return "seleccionTests";
    }

    @RequestMapping("/testAutismo")
    public ModelAndView mostrarFormularioAutismo() {
        ModelMap modelo = new ModelMap();
        modelo.put("modeloTest", new ModeloTest());
        return new ModelAndView("testAutismo", modelo);
    }

    @RequestMapping("/testAsperger")
    public ModelAndView mostrarFormularioAsperger() {
        ModelMap modelo = new ModelMap();
        modelo.put("modeloTest", new ModeloTest());
        return new ModelAndView("testAsperger", modelo);
    }


    @RequestMapping("/testDeficitDeAtencion")
    public ModelAndView mostrarFormularioTdha() {
        ModelMap modelo = new ModelMap();
        modelo.put("modeloTest", new ModeloTest());
        return new ModelAndView("testDeficitDeAtencion", modelo);
    }

    @RequestMapping("/testDislexia")
    public ModelAndView mostrarFormularioDislexia() {
        ModelMap modelo = new ModelMap();
        modelo.put("modeloTest", new ModeloTest());
        return new ModelAndView("testDislexia", modelo);
    }

    @RequestMapping("/testAutismo/submit")
    public ModelAndView procesarTestAutismo(@ModelAttribute("modeloTest") ModeloTest modeloTest, ModelMap model) {
        String resultado = servicioTest.calcularResultadoAutismo(modeloTest);
        model.put("resultado", resultado);
        return new ModelAndView("resultadoTest", model);
    }


    @RequestMapping("/testAsperger/submit")
    public ModelAndView procesarTestAsperger(@ModelAttribute("modeloTest") ModeloTest modeloTest, ModelMap model) {
        String resultado = servicioTest.calcularResultadoAsperger(modeloTest);
        model.put("resultado", resultado);
        return new ModelAndView("resultadoTest", model);
    }

    @RequestMapping("/testTdha/submit")
    public ModelAndView procesarTestTdha(@ModelAttribute("modeloTest") ModeloTest modeloTest, ModelMap model) {
        String resultado = servicioTest.calcularResultadoTdha(modeloTest);
        model.put("resultado", resultado);
        return new ModelAndView("resultadoTest", model);
    }

    @RequestMapping("/testDislexia/submit")
    public ModelAndView procesarTestDislexia(@ModelAttribute("modeloTest") ModeloTest modeloTest, ModelMap model) {
        String resultado = servicioTest.calcularResultadoDislexia(modeloTest);
        model.put("resultado", resultado);
        return new ModelAndView("resultadoTest", model);
    }

    @GetMapping("/crearTest")
    public String mostrarFormularioCrearTest(Model model) {
        model.addAttribute("modeloTest", new ModeloTest());
        return "crearTest";
    }

    @PostMapping("/guardarModeloTest")
    public String guardarModeloTest(@ModelAttribute("modeloTest") ModeloTest modeloTest) {
        servicioTest.crearTest(modeloTest);
        return "redirect:/crearTest"; // Redirigir al formulario para crear más tests
    }

    @GetMapping("/crearPregunta")
    public String mostrarFormularioCrearPregunta(Model model) {
        List<ModeloTest> modelosTest = servicioTest.obtenerTodosLosTests();
        model.addAttribute("modelosTest", modelosTest);
        model.addAttribute("preguntaTest", new PreguntaTest());
        return "crearPregunta";
    }

    @PostMapping("/guardarPregunta")
    public String guardarPregunta(@ModelAttribute("preguntaTest") PreguntaTest preguntaTest, @RequestParam("modeloTestId") Long modeloTestId) {
        Optional<ModeloTest> modeloTestOpt = servicioTest.obtenerTestPorId(modeloTestId);
        if (modeloTestOpt.isPresent()) {
            preguntaTest.setModeloTest(modeloTestOpt.get());
            servicioTest.guardarPregunta(preguntaTest);
        } else {
            return "redirect:/pregunta/crear?error=ModeloTestNotFound";
        }
        return "redirect:/crearPregunta";
    }

    @GetMapping("/crearRespuesta")
    public String mostrarFormularioCrearRespuesta(Model model) {
        List<ModeloTest> modelosTest = servicioTest.obtenerTodosLosTests();
        model.addAttribute("modelosTest", modelosTest);
        model.addAttribute("respuestaTest", new RespuestaTest());
        return "crearRespuesta";
    }

    @PostMapping("/guardarRespuesta")
    public String guardarRespuesta(@ModelAttribute("respuestaTest") RespuestaTest respuestaTest, @RequestParam("modeloTestId") Long modeloTestId) {
        Optional<ModeloTest> modeloTestOpt = servicioTest.obtenerTestPorId(modeloTestId);
        if (modeloTestOpt.isPresent()) {
            respuestaTest.setModeloTest(modeloTestOpt.get());
            servicioTest.guardarRespuesta(respuestaTest);
        } else {
            return "redirect:/respuesta/crear?error=ModeloTestNotFound";
        }
        return "redirect:/crearRespuesta";
    }
/////////////////////////////////////////////////////////////////////////



    @GetMapping("/editarTest/{id}")
    public String mostrarFormularioEditarTest(@PathVariable("id") Long id, Model model) {
        Optional<ModeloTest> testOpt = servicioTest.obtenerTestPorId(id);
        if (testOpt.isPresent()) {
            model.addAttribute("modeloTest", testOpt.get());
        } else {
            return "redirect:/crudTest?error=TestNoEncontrado";
        }
        return "crearTest";
    }

    @GetMapping("/borrarTest/{id}")
    public String borrarTest(@PathVariable("id") Long id) {
        servicioTest.eliminarTest(id);
        return "redirect:/crudTest";
    }

    @GetMapping("/editarPregunta/{id}")
    public String mostrarFormularioEditarPregunta(@PathVariable("id") Long id, Model model) {
        Optional<PreguntaTest> preguntaOpt = servicioTest.obtenerPreguntaPorId(id);
        if (preguntaOpt.isPresent()) {
            PreguntaTest preguntaTest = preguntaOpt.get();
            model.addAttribute("preguntaTest", preguntaTest);
            List<ModeloTest> modelosTest = servicioTest.obtenerTodosLosTests();
            model.addAttribute("modelosTest", modelosTest);
            model.addAttribute("modeloTestId", preguntaTest.getModeloTest().getId()); // Pasar el ID del test actual
            return "crearPregunta"; // Utilizando la misma vista para editar
        } else {
            return "redirect:/crudTest?error=PreguntaNoEncontrada";
        }
    }


    @GetMapping("/borrarPregunta/{id}")
    public String borrarPregunta(@PathVariable("id") Long id) {
        servicioTest.eliminarPregunta(id);
        return "redirect:/crudTest";
    }

    @GetMapping("/editarRespuesta/{id}")
    public String mostrarFormularioEditarRespuesta(@PathVariable("id") Long id, Model model) {
        Optional<RespuestaTest> respuestaOpt = servicioTest.obtenerRespuestaPorId(id);
        if (respuestaOpt.isPresent()) {
            RespuestaTest respuestaTest = respuestaOpt.get();
            model.addAttribute("respuestaTest", respuestaTest);
            List<ModeloTest> modelosTest = servicioTest.obtenerTodosLosTests();
            model.addAttribute("modelosTest", modelosTest);
            model.addAttribute("modeloTestId", respuestaTest.getModeloTest().getId()); // Pasar el ID del test actual
            return "crearRespuesta"; // Utilizando la misma vista para editar
        } else {
            return "redirect:/crudTest?error=RespuestaNoEncontrada";
        }
    }

    @GetMapping("/borrarRespuesta/{id}")
    public String borrarRespuesta(@PathVariable("id") Long id) {
        servicioTest.eliminarRespuesta(id);
        return "redirect:/crudTest";
    }

    @GetMapping("/crudTest")
    public String listarTests(@RequestParam(name = "id", required = false) Long id, Model model) {
        List<ModeloTest> tests = servicioTest.obtenerTodosLosTests();
        model.addAttribute("tests", tests);

        if (id != null) {
            Optional<ModeloTest> testOpt = servicioTest.obtenerTestPorId(id);
            if (testOpt.isPresent()) {
                ModeloTest modeloTest = testOpt.get();
                List<PreguntaTest> preguntas = servicioTest.obtenerPreguntasPorModeloTestId(id);
                List<RespuestaTest> respuestas = servicioTest.obtenerRespuestasPorModeloTestId(id);
                model.addAttribute("preguntas", preguntas);
                model.addAttribute("respuestas", respuestas);
                model.addAttribute("selectTest", modeloTest);
            }
        }

        return "crudTest";
    }
}