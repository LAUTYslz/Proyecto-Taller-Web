package com.tallerwebi.dominio;

import javax.servlet.http.HttpServletRequest;

public interface ServicioMembresiaActivada {








    Consulta realizarConsulta(Consulta consulta, Long hijo, Long profesional, Usuario usuario);

    Consulta buscarConsultaPorUsuario(Long usuarioid);
}
