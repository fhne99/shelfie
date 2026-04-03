package com.projet.shelfie.domain.port.out;

public interface EmailPort {
    void sendConfirmation(String email, String token);
    void sendPasswordReset(String email, String token);
}