package com.tallerwebi.dominio.enums;

public enum ResultadoDislexia {
    NO_DETECTADO("No Se Observan Indicaciones de Dislexia. " +
            "Después de completar el test, no se han identificado indicios significativos que sugieran la " +
            "presencia de dislexia. Sin embargo, si tiene preocupaciones sobre el rendimiento académico o " +
            "el desarrollo de habilidades de lectura y escritura, le recomendamos que consulte con un " +
            "profesional de la salud o un especialista en educación para obtener una evaluación más detallada."),

    ALGUNOS_INDICIOS("Se Observan Algunos Indicios que Podrían Ser Indicativos de Dislexia. " +
            "Después de completar el test, se han identificado algunos indicios que podrían ser " +
            "indicativos de dislexia. Es importante recordar que estos resultados son " +
            "preliminares y que se necesita una evaluación más detallada para confirmar cualquier diagnóstico. " +
            "Le recomendamos que busque el consejo de un profesional de la salud infantil para una evaluación " +
            "adicional y para obtener más orientación sobre los pasos a seguir."),

    ALTA_PROBABILIDAD("Resultados Sugieren una Alta Probabilidad de Dislexia. " +
            "Después de completar el test, los resultados sugieren una alta probabilidad de dislexia. " +
            "Es crucial obtener una evaluación detallada de un especialista en dislexia. Un plan de intervención " +
            "intensivo, que incluya tutorías especializadas y apoyo constante, será esencial para superar las " +
            "dificultades de lectura y escritura. Le recomendamos que consulte a un especialista en dislexia " +
            "o a un psicólogo educativo para un diagnóstico y un plan de intervención adecuados.");

    private final String texto;

    ResultadoDislexia(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
