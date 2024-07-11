package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.enums.ResultadoAsperger;
import com.tallerwebi.dominio.enums.ResultadoAutismo;
import com.tallerwebi.dominio.enums.ResultadoDislexia;
import com.tallerwebi.dominio.enums.ResultadoTDHA;
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

        for (RespuestaTest respuesta : respuestas) {
            if ("si".equalsIgnoreCase(respuesta.getTexto())) {
                contadorRespuestasSi++;
            }
        }

        ResultadoAsperger resultado = obtenerResultadoAsperger(contadorRespuestasSi);
        return resultado.getTexto();
    }

    private ResultadoAsperger obtenerResultadoAsperger(int contadorRespuestasSi) {
        if (contadorRespuestasSi <= 3) {
            return ResultadoAsperger.NO_DETECTADO;
        } else if (contadorRespuestasSi <= 6) {
            return ResultadoAsperger.ALGUNOS_INDICIOS;
        } else {
            return ResultadoAsperger.ALTA_PROBABILIDAD;
        }
    }

    @Override
    public String calcularResultadoTdha(ModeloTest modeloTest) {
        int contadorRespuestasSi = 0;
        List<RespuestaTest> respuestas = modeloTest.getRespuestas();

        for (RespuestaTest respuesta : respuestas) {
            if ("si".equalsIgnoreCase(respuesta.getTexto())) {
                contadorRespuestasSi++;
            }
        }

        ResultadoTDHA resultado = obtenerResultadoTdha(contadorRespuestasSi);
        return resultado.getTexto();
    }

    private ResultadoTDHA obtenerResultadoTdha(int contadorRespuestasSi) {
        if (contadorRespuestasSi <= 3) {
            return ResultadoTDHA.NO_DETECTADO;
        } else if (contadorRespuestasSi <= 6) {
            return ResultadoTDHA.ALGUNOS_INDICIOS;
        } else {
            return ResultadoTDHA.ALTA_PROBABILIDAD;
        }
    }

    @Override
    public String calcularResultadoDislexia(ModeloTest modeloTest) {
        int contadorRespuestasSi = 0;
        List<RespuestaTest> respuestas = modeloTest.getRespuestas();

        for (RespuestaTest respuesta : respuestas) {
            if ("si".equalsIgnoreCase(respuesta.getTexto())) {
                contadorRespuestasSi++;
            }
        }

        ResultadoDislexia resultado = obtenerResultadoDislexia(contadorRespuestasSi);
        return resultado.getTexto();
    }

    private ResultadoDislexia obtenerResultadoDislexia(int contadorRespuestasSi) {
        if (contadorRespuestasSi <= 3) {
            return ResultadoDislexia.NO_DETECTADO;
        } else if (contadorRespuestasSi <= 6) {
            return ResultadoDislexia.ALGUNOS_INDICIOS;
        } else {
            return ResultadoDislexia.ALTA_PROBABILIDAD;
        }
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



