package com.tallerwebi.dominio.excepcion;

public class TipoNoEncontradoPorID extends RuntimeException {
    public TipoNoEncontradoPorID(Long idTipo) {
        super("No se pudo encontrar el tipo de contacto con el id: " + idTipo);
    }
}
