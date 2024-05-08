package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tallerwebi.presentacion.RespuestasTest;
import javax.transaction.Transactional;


@Service("servicioTest")
@Transactional
public class ServicioTestImpl implements ServicioTest {

    private RespuestasTest respuestasTest;

    @Override
    public String calcularResultado(RespuestasTest respuestasTest) {
        return "algo";
    }

}