package com.miguel.vendix.business.services.email;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
    private JavaMailSender javaMailSender;

    public void enviarCorreo(String destinatario, String asunto, String mensaje) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("app.vendix@gmail.com"); // Tu correo de Gmail
        email.setTo(destinatario);
        email.setSubject(asunto);
        email.setText(mensaje);

        System.out.println("Enviando gmail para.... "+destinatario);
        
        javaMailSender.send(email);
    }
}
