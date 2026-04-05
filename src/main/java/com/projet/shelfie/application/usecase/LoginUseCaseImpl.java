package com.projet.shelfie.application.usecase;

import com.projet.shelfie.domain.exception.UnauthorizedException;
import com.projet.shelfie.domain.port.in.LoginUseCase;
import com.projet.shelfie.domain.port.out.TokenGeneratorPort;
import com.projet.shelfie.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGeneratorPort tokenGeneratorPort;

    @Override
    public LoginResult login(LoginCommand command) {
        var user = userRepository.findByEmail(command.email())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!user.isActive()) {
            throw new UnauthorizedException("Account not activated");
        }

        if (!passwordEncoder.matches(command.password(), user.passwordHash())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        var token = tokenGeneratorPort.generateToken(user.id());
        return new LoginResult(token, 86400L);
    }
}