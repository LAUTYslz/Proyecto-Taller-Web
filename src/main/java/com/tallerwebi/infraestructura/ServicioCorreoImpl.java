package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.ServicioCorreo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioCorreoImpl implements ServicioCorreo {

    private JavaMailSender mailSender;

    @Autowired
    public ServicioCorreoImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviarCorreo(String email, String asunto, String cuerpo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(asunto);
        message.setText(cuerpo);
        message.setFrom("tallerweb67@gmail.com");

        mailSender.send(message);
    }

}
