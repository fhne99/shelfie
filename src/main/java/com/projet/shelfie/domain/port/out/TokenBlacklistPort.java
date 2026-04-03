package com.projet.shelfie.domain.port.out;

import java.time.Instant;

public interface TokenBlacklistPort {
    void blacklist(String jti, Instant expiresAt);
    boolean isBlacklisted(String jti);
    void deleteExpired();
}