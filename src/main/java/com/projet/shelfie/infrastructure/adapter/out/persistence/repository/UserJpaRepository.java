package com.projet.shelfie.infrastructure.adapter.out.persistence.repository;

import com.projet.shelfie.infrastructure.adapter.out.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {
    Optional<UserJpaEntity> findByEmail(String email);
    Optional<UserJpaEntity> findByPseudo(String pseudo);
    boolean existsByEmail(String email);
    boolean existsByPseudo(String pseudo);
}