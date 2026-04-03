package com.projet.shelfie.domain.port.in;

public interface LoginUseCase {
    LoginResult login(LoginCommand command);

    record LoginCommand(String email, String password) {}
    record LoginResult(String accessToken, long expiresIn) {}
}