package com.tallerwebi.dominio.enums;

public enum Vacuna {
    BCG("BCG (Tuberculosis)", 0),
    HEPATITIS_B_PRIMERA("Hepatitis B (primera dosis)", 0),
    PENTAVALENTE_PRIMERA("Pentavalente (primera dosis)", 2),
    POLIOMIELITIS_PRIMERA("Poliomielitis (IPV)", 2),
    NEUMOCOCO_CONJUGADA_PRIMERA("Neumococo Conjugada (PCV13)", 2),
    ROTAVIRUS_PRIMERA("Rotavirus (primera dosis)", 2),
    MENINGOCOCO_PRIMERA("Meningococo (primera dosis)", 3),
    PENTAVALENTE_SEGUNDA("Pentavalente (segunda dosis)", 4),
    POLIOMIELITIS_SEGUNDA("Poliomielitis (IPV, segunda dosis)", 4),
    NEUMOCOCO_CONJUGADA_SEGUNDA("Neumococo Conjugada (PCV13, segunda dosis)", 4),
    ROTAVIRUS_SEGUNDA("Rotavirus (segunda dosis)", 4),
    MENINGOCOCO_SEGUNDA("Meningococo (segunda dosis)", 5),
    PENTAVALENTE_TERCERA("Pentavalente (tercera dosis)", 6),
    POLIOMIELITIS_TERCERA("Poliomielitis (OPV, tercera dosis)", 6),
    GRIPE("Gripe (Influenza, en los meses de campaña antigripal)", 6),
    TRIPLE_VIRAL("Triple Viral (SRP: Sarampión, Rubéola, Paperas)", 12),
    NEUMOCOCO_CONJUGADA_REFUERZO("Neumococo Conjugada (PCV13, refuerzo)", 12),
    HEPATITIS_A("Hepatitis A (única dosis)", 12),
    VARICELA("Varicela", 15),
    MENINGOCOCO_REFUERZO("Meningococo (refuerzo)", 15),
    TRIPLE_BACTERIANA_CELULAR("Triple Bacteriana Celular (DTP: Difteria, Tétanos, Tos convulsa)", 18),
    POLIOMIELITIS_REFUERZO("Poliomielitis (OPV, refuerzo)", 18),
    TRIPLE_VIRAL_REFUERZO("Triple Viral (SRP, refuerzo)", 60),
    TRIPLE_BACTERIANA_CELULAR_REFUERZO("Triple Bacteriana Celular (DTP, refuerzo)", 60),
    POLIOMIELITIS_REFUERZO_ESCOLAR("Poliomielitis (OPV, refuerzo escolar)", 60),
    TRIPLE_BACTERIANA_ACELULAR("Triple Bacteriana Acelular (dTpa: Difteria, Tétanos, Tos convulsa)", 132),
    VPH("VPH (Virus del Papiloma Humano, en niñas y niños)", 132),
    MENINGOCOCO_REFUERZO_ONCE("Meningococo (refuerzo)", 132);

    private final String nombreVacuna;
    private final int edadMeses;

    Vacuna(String nombreVacuna, int edadMeses) {
        this.nombreVacuna = nombreVacuna;
        this.edadMeses = edadMeses;
    }

    public String getNombreVacuna() {
        return nombreVacuna;
    }

    public int getEdadMeses() {
        return edadMeses;
    }

}