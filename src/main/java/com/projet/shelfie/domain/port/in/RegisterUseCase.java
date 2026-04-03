package com.projet.shelfie.domain.port.in;

import com.projet.shelfie.domain.model.User;

public interface RegisterUseCase {
    User register(RegisterCommand command);

    record RegisterCommand(
            String email,
            String pseudo,
            String password
    ) {}
}