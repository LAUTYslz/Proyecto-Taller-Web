package com.tallerwebi.dominio;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RepositorioMembresiaActivada {




    Consulta guardarConsulta(Consulta consulta);

    void actualizarConsulta(Consulta consulta);





    List <Consulta> buscarConsulta(Long usuarioid);





    List<Consulta> buscarConsultasPorProfesionales(String email);

    Consulta buscarConsultaPorId(Long consultaId);

    List<Consulta> todasLasConsultasCreadas();
}
