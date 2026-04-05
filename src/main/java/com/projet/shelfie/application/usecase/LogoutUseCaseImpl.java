package com.projet.shelfie.application.usecase;

import com.projet.shelfie.domain.port.in.LogoutUseCase;
import com.projet.shelfie.domain.port.out.TokenBlacklistPort;
import com.projet.shelfie.domain.port.out.TokenGeneratorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutUseCaseImpl implements LogoutUseCase {

    private final TokenGeneratorPort tokenGeneratorPort;
    private final TokenBlacklistPort tokenBlacklistPort;

    @Override
    public void logout(LogoutCommand command) {
        if (!tokenGeneratorPort.isValid(command.token())) {
            return;
        }
        var jti = tokenGeneratorPort.extractJti(command.token());
        var expiration = tokenGeneratorPort.extractExpiration(command.token());
        tokenBlacklistPort.blacklist(jti, expiration);
    }
}