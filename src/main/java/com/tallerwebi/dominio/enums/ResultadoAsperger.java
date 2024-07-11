package com.tallerwebi.dominio.enums;

public enum ResultadoAsperger {
    NO_DETECTADO("No se Detectan Indicios de Síndrome de Asperger. Tras completar el test, " +
            "los resultados indican que no se han detectado indicios de síndrome de Asperger " +
            "en este momento. Es importante tener en cuenta que este test es una herramienta " +
            "inicial y que los resultados pueden variar. Siempre es recomendable seguir observando " +
            "el desarrollo de su hijo y consultar con un profesional de la salud infantil si surgen " +
            "preocupaciones adicionales en el futuro."),

    ALGUNOS_INDICIOS("Se Observan Algunos Indicios que Podrían Ser Indicativos de Síndrome de Asperger. " +
            "Después de completar el test, se han identificado algunos indicios que podrían ser " +
            "indicativos de síndrome de Asperger. Es importante recordar que estos resultados son " +
            "preliminares y que se necesita una evaluación más detallada para confirmar cualquier diagnóstico. " +
            "Le recomendamos que busque el consejo de un profesional de la salud infantil para una evaluación " +
            "adicional y para obtener más orientación sobre los pasos a seguir."),

    ALTA_PROBABILIDAD("Los Resultados Sugieren una Alta Probabilidad de Síndrome de Asperger. Basándose en los " +
            "resultados del test, se sugiere una alta probabilidad de síndrome de Asperger. Es importante " +
            "comprender que estos resultados son generados por un algoritmo y que no reemplazan la evaluación " +
            "de un profesional médico. Le recomendamos encarecidamente que consulte con un profesional de la salud " +
            "infantil para una evaluación más exhaustiva y para obtener un diagnóstico preciso. El apoyo y la orientación " +
            "de un profesional pueden ser fundamentales para ayudar a su hijo a recibir el cuidado y el apoyo necesarios.");

    private final String texto;

    ResultadoAsperger(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
