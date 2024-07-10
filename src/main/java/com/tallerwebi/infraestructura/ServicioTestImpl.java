package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.enums.ResultadoAutismo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service("servicioTest")
@Transactional
public class ServicioTestImpl implements ServicioTest {

    private final ModeloTestRepositorio modeloTestRepositorio;
    private final PreguntaTestRepositorio preguntaTestRepositorio;
    private final RespuestaTestRepositorio respuestaTestRepositorio;


    @Autowired
    public ServicioTestImpl(ModeloTestRepositorio modeloTestRepositorio, PreguntaTestRepositorio preguntaTestRepositorio, RespuestaTestRepositorio respuestaTestRepositorio) {
        this.modeloTestRepositorio = modeloTestRepositorio;
        this.preguntaTestRepositorio = preguntaTestRepositorio;
        this.respuestaTestRepositorio = respuestaTestRepositorio;
    }

    @Override
    public String calcularResultadoAutismo(ModeloTest modeloTest) {
        int contadorRespuestasSi = 0;
        List<RespuestaTest> respuestas = modeloTest.getRespuestas();

        for (RespuestaTest respuesta : respuestas) {
            if ("si".equalsIgnoreCase(respuesta.getTexto())) {
                contadorRespuestasSi++;
            }
        }

        ResultadoAutismo resultado = obtenerResultadoAutismo(contadorRespuestasSi);
        return resultado.getTexto();
    }

    private ResultadoAutismo obtenerResultadoAutismo(int contadorRespuestasSi) {
        if (contadorRespuestasSi <= 3) {
            return ResultadoAutismo.NO_DETECTADO;
        } else if (contadorRespuestasSi <= 6) {
            return ResultadoAutismo.ALGUNOS_INDICIOS;
        } else {
            return ResultadoAutismo.ALTA_PROBABILIDAD;
        }
    }

    @Override
    public String calcularResultadoAsperger(ModeloTest modeloTest) {
        int contadorRespuestasSi = 0;
        List<RespuestaTest> respuestas = modeloTest.getRespuestas();
        String textoResultadoAsperger = null;

        for (RespuestaTest respuesta : respuestas) {
            if ("si".equals(respuesta.getTexto())) {
                contadorRespuestasSi++;
            }
        }

        if (contadorRespuestasSi <= 3) {
            textoResultadoAsperger= "No se Detectan Indicios de Síndrome de Asperger. " +
                    "Tras completar el test, los resultados indican que no se han detectado indicios " +
                    "de síndrome de Asperger en este momento. Es importante tener en cuenta " +
                    "que este test es una herramienta inicial y que los resultados " +
                    "pueden variar. Siempre es recomendable seguir observando " +
                    "el desarrollo de su hijo y consultar con un profesional de la salud" +
                    " infantil si surgen preocupaciones adicionales en el futuro.";
        } else if (contadorRespuestasSi <= 7) {
            textoResultadoAsperger= "Se Observan Algunos Indicios que Podrían Ser Indicativos de Síndrome de Asperger. " +
                    "Después de completar el test, se han identificado algunos indicios " +
                    "que podrían ser indicativos de síndrome de Asperger. Es importante recordar " +
                    "que estos resultados son preliminares y que se necesita una " +
                    "evaluación más detallada para confirmar cualquier diagnóstico. " +
                    "Le recomendamos que busque el consejo de un profesional de la salud" +
                    " infantil para una evaluación adicional y para obtener más " +
                    "orientación sobre los pasos a seguir.";
        } else {
            textoResultadoAsperger= "Los Resultados Sugieren una Alta Probabilidad de Síndrome de Asperger. " +
                    "Basándose en los resultados del test, se sugiere una alta " +
                    "probabilidad de síndrome de Asperger. Es importante comprender que estos resultados" +
                    " son generados por un algoritmo y que no reemplazan la evaluación " +
                    "de un profesional médico. Le recomendamos encarecidamente que" +
                    " consulte con un profesional de la salud infantil para una evaluación " +
                    "más exhaustiva y para obtener un diagnóstico preciso. " +
                    "El apoyo y la orientación de un profesional pueden ser fundamentales " +
                    "para ayudar a su hijo a recibir el cuidado y el apoyo necesarios.";
        }
        return textoResultadoAsperger;
    }





    @Override
    public void crearTest(ModeloTest modeloTest) {
        for (PreguntaTest pregunta : modeloTest.getPreguntas()) {
            pregunta.setModeloTest(modeloTest);
        }
        for (RespuestaTest respuesta : modeloTest.getRespuestas()) {
            respuesta.setModeloTest(modeloTest);
        }
        modeloTestRepositorio.guardar(modeloTest);
    }

    @Override
    public List<ModeloTest> obtenerTodosLosTests() {
        return modeloTestRepositorio.obtenerTodos();
    }

    @Override
    public void guardarPregunta(PreguntaTest preguntaTest) {
        preguntaTestRepositorio.guardar(preguntaTest);
    }

    @Override
    public List<PreguntaTest> obtenerTodasLasPreguntas() {

        return preguntaTestRepositorio.obtenerTodos();
    }

    @Override
    public void guardarRespuesta(RespuestaTest respuestaTest) {

        respuestaTestRepositorio.guardar(respuestaTest);
    }

    @Override
    public List<RespuestaTest> obtenerTodasLasRespuestas() {

        return respuestaTestRepositorio.obtenerTodos();
    }

    @Override
    public Optional<ModeloTest> obtenerTestPorId(Long id) {

        return modeloTestRepositorio.findById(id);
    }

    @Override
    public void editarTest(ModeloTest modeloTest) {
        modeloTestRepositorio.guardar(modeloTest);
    }

    @Override
    public void eliminarTest(Long id) {
        modeloTestRepositorio.eliminarPorId(id);
    }

    @Override
    public void editarPregunta(PreguntaTest preguntaTest) {
        preguntaTestRepositorio.editarPregunta(preguntaTest);
    }

    @Override
    public void eliminarPregunta(Long id) {
        preguntaTestRepositorio.eliminarPorId(id);
    }

    @Override
    public void editarRespuesta(RespuestaTest respuestaTest) {
        respuestaTestRepositorio.editarRespuesta(respuestaTest);
    }

    @Override
    public void eliminarRespuesta(Long id) {

        respuestaTestRepositorio.eliminarPorId(id);
    }

    @Override
    public Optional<PreguntaTest> obtenerPreguntaPorId(Long id) {
        return preguntaTestRepositorio.findById(id);
    }

    @Override
    public Optional<RespuestaTest> obtenerRespuestaPorId(Long id) {
        return respuestaTestRepositorio.findById(id);
    }

    @Override
    public List<RespuestaTest> obtenerRespuestasPorModeloTestId(Long modeloTestId) {
        return respuestaTestRepositorio.obtenerRespuestasPorModeloTestId(modeloTestId);
    }

    @Override
    public List<PreguntaTest> obtenerPreguntasPorModeloTestId(Long modeloTestId) {
        return preguntaTestRepositorio.obtenerPreguntasPorModeloTestId(modeloTestId);
    }
}



