package com.projet.shelfie.application.usecase;

import com.projet.shelfie.domain.exception.EmailAlreadyExistsException;
import com.projet.shelfie.domain.exception.PseudoAlreadyExistsException;
import com.projet.shelfie.domain.model.User;
import com.projet.shelfie.domain.model.UserRole;
import com.projet.shelfie.domain.port.in.RegisterUseCase;
import com.projet.shelfie.domain.port.out.EmailPort;
import com.projet.shelfie.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterUseCaseImpl implements RegisterUseCase {

    private final UserRepository userRepository;
    private final EmailPort emailPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new EmailAlreadyExistsException(command.email());
        }
        if (userRepository.existsByPseudo(command.pseudo())) {
            throw new PseudoAlreadyExistsException(command.pseudo());
        }

        var now = Instant.now();
        var user = new User(
                UUID.randomUUID(),
                command.email(),
                command.pseudo(),
                passwordEncoder.encode(command.password()),
                null,
                null,
                UserRole.ROLE_USER,
                false,
                false,
                now,
                now
        );

        var saved = userRepository.save(user);
        emailPort.sendConfirmation(saved.email(), UUID.randomUUID().toString());
        return saved;
    }
}