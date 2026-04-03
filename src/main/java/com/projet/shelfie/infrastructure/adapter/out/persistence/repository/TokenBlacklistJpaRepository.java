package com.projet.shelfie.infrastructure.adapter.out.persistence.repository;

import com.projet.shelfie.infrastructure.adapter.out.persistence.entity.TokenBlacklistJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

public interface TokenBlacklistJpaRepository
        extends JpaRepository<TokenBlacklistJpaEntity, UUID> {

    boolean existsByJti(String jti);

    @Modifying
    @Transactional
    @Query("DELETE FROM TokenBlacklistJpaEntity t WHERE t.expiresAt < :now")
    void deleteByExpiresAtBefore(Instant now);
}