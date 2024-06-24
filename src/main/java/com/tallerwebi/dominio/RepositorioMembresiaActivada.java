package com.tallerwebi.dominio;

import javax.servlet.http.HttpServletRequest;

public interface RepositorioMembresiaActivada {




    Consulta guardarConsulta(Consulta consulta);

    void actualizarConsulta(Consulta consulta);





    Consulta buscarConsulta(Long usuarioid);
}
