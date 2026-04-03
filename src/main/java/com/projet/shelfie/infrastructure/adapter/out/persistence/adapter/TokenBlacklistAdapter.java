package com.projet.shelfie.infrastructure.adapter.out.persistence.adapter;

import com.projet.shelfie.domain.port.out.TokenBlacklistPort;
import com.projet.shelfie.infrastructure.adapter.out.persistence.entity.TokenBlacklistJpaEntity;
import com.projet.shelfie.infrastructure.adapter.out.persistence.repository.TokenBlacklistJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenBlacklistAdapter implements TokenBlacklistPort {

    private final TokenBlacklistJpaRepository jpaRepository;

    @Override
    public void blacklist(String jti, Instant expiresAt) {
        var entity = new TokenBlacklistJpaEntity();
        entity.setId(UUID.randomUUID());
        entity.setJti(jti);
        entity.setExpiresAt(expiresAt);
        entity.setCreatedAt(Instant.now());
        jpaRepository.save(entity);
    }

    @Override
    public boolean isBlacklisted(String jti) {
        return jpaRepository.existsByJti(jti);
    }

    @Override
    public void deleteExpired() {
        jpaRepository.deleteByExpiresAtBefore(Instant.now());
    }
}