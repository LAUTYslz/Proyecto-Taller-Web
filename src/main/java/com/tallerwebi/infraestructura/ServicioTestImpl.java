package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioTest;
import com.tallerwebi.presentacion.RespuestaTest;
import org.springframework.stereotype.Service;
import com.tallerwebi.dominio.ModeloTest;
import javax.transaction.Transactional;
import java.util.List;


@Service("servicioTest")
@Transactional
public class ServicioTestImpl implements ServicioTest {

    @Override
    public String calcularResultadoAutismo(ModeloTest modeloTest) {
        int contadorRespuestasSi = 0;
        List<RespuestaTest> respuestas = modeloTest.getRespuestas();
        String textoResultadoAutismo = null;

        for (RespuestaTest respuesta : respuestas) {
            if ("si".equals(respuesta.getTexto())) {
                contadorRespuestasSi++;
            }
        }

        if (contadorRespuestasSi <= 3) {
            textoResultadoAutismo= "No se Detectan Indicios de Autismo. Tras completar el test, " +
                    "los resultados indican que no se han detectado indicios " +
                    "de autismo en este momento. Es importante tener en cuenta " +
                    "que este test es una herramienta inicial y que los resultados " +
                    "pueden variar. Siempre es recomendable seguir observando " +
                    "el desarrollo de su hijo y consultar con un profesional de la salud" +
                    " infantil si surgen preocupaciones adicionales en el futuro.";
        } else if (contadorRespuestasSi <= 6) {
            textoResultadoAutismo= "Se Observan Algunos Indicios que Podrían Ser Indicativos de Autismo" +
                    "Después de completar el test, se han identificado algunos indicios " +
                    "que podrían ser indicativos de autismo. Es importante recordar " +
                    "que estos resultados son preliminares y que se necesita una " +
                    "evaluación más detallada para confirmar cualquier diagnóstico. " +
                    "Le recomendamos que busque el consejo de un profesional de la salud" +
                    " infantil para una evaluación adicional y para obtener más " +
                    "orientación sobre los pasos a seguir.";
        } else {
            textoResultadoAutismo= "Los Resultados Sugieren una Alta Probabilidad de Autismo. " +
                    " Basándose en los resultados del test, se sugiere una alta " +
                    "probabilidad de autismo. Es importante comprender que estos resultados" +
                    " son generados por un algoritmo y que no reemplazan la evaluación " +
                    "de un profesional médico. Le recomendamos encarecidamente que" +
                    " consulte con un profesional de la salud infantil para una evaluación " +
                    "más exhaustiva y para obtener un diagnóstico preciso. " +
                    "El apoyo y la orientación de un profesional pueden ser fundamentales " +
                    "para ayudar a su hijo a recibir el cuidado y el apoyo necesarios." +
                    "En el siguiente enlace te dejamos una lista de profesionales cerca " +
                    "de tu hogar para que tu niño y vos hagan una consulta";
        }
        return textoResultadoAutismo;
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
}



