package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CantidadDeConsultasAgotadas;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ServicioMembresiaActivada {








    Consulta realizarConsulta(Consulta consulta, Long hijo, Long profesional, Usuario usuario) throws CantidadDeConsultasAgotadas;



    List<Consulta> buscarConsultaPorUsuario(Long usuarioid);
}
