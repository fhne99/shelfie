package com.projet.shelfie.application.usecase;

import com.projet.shelfie.domain.exception.UnauthorizedException;
import com.projet.shelfie.domain.port.in.LoginUseCase;
import com.projet.shelfie.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

        // Le token JWT sera généré par JwtService dans l'adaptateur web
        // On retourne un résultat intermédiaire — le controller s'occupera du token
        return new LoginResult("__pending__", 86400L);
    }
}