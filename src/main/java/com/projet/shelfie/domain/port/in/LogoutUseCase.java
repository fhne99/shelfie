package com.projet.shelfie.domain.port.in;

public interface LogoutUseCase {
    void logout(LogoutCommand command);

    record LogoutCommand(String token) {}
}