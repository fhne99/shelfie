package com.projet.shelfie.infrastructure.config;

import com.projet.shelfie.domain.port.out.TokenGeneratorPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;
import java.time.Instant;

@Service
public class JwtService implements TokenGeneratorPort {

    private final SecretKey secretKey;
    private final long expiration;

    public JwtService(
            @Value("${shelfie.jwt.secret}") String secret,
            @Value("${shelfie.jwt.expiration}") long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration = expiration;
    }

    @Override
    public String generateToken(UUID userId) {
        var jti = UUID.randomUUID().toString();
        return Jwts.builder()
                .subject(userId.toString())
                .id(jti)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String extractUserId(String token) {
        return parseClaims(token).getSubject();
    }

    @Override
    public Instant extractExpiration(String token) {
        return parseClaims(token).getExpiration().toInstant();
    }

    @Override
    public String extractJti(String token) {
        return parseClaims(token).getId();
    }


    @Override
    public boolean isValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}