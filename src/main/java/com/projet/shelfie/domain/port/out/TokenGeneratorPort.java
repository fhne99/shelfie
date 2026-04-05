package com.projet.shelfie.domain.port.out;

import java.time.Instant;
import java.util.UUID;

public interface TokenGeneratorPort {
    String generateToken(UUID userId);
    String extractJti(String token);
    Instant extractExpiration(String token);
    boolean isValid(String token);
    String extractUserId(String token);
}