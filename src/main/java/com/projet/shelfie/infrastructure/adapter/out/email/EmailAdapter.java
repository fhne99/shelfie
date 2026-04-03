package com.projet.shelfie.infrastructure.adapter.out.email;

import com.projet.shelfie.domain.port.out.EmailPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailAdapter implements EmailPort {

    private final JavaMailSender mailSender;

    @Value("${shelfie.mail.from}")
    private String from;

    @Value("${shelfie.mail.base-url}")
    private String baseUrl;

    @Override
    public void sendConfirmation(String email, String token) {
        var message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("Confirmez votre compte Shelfie");
        message.setText(
                "Bonjour,\n\n" +
                        "Cliquez sur ce lien pour confirmer votre compte :\n" +
                        baseUrl + "/api/auth/confirm?token=" + token + "\n\n" +
                        "Ce lien est valable 24h.\n\n" +
                        "L'équipe Shelfie"
        );
        mailSender.send(message);
    }

    @Override
    public void sendPasswordReset(String email, String token) {
        var message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("Réinitialisation de votre mot de passe Shelfie");
        message.setText(
                "Bonjour,\n\n" +
                        "Cliquez sur ce lien pour réinitialiser votre mot de passe :\n" +
                        baseUrl + "/reset-password?token=" + token + "\n\n" +
                        "Ce lien est valable 1h.\n\n" +
                        "L'équipe Shelfie"
        );
        mailSender.send(message);
    }
}