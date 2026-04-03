package com.projet.shelfie.infrastructure.adapter.out.persistence.repository;

import com.projet.shelfie.infrastructure.adapter.out.persistence.entity.UserBookJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserBookJpaRepository extends JpaRepository<UserBookJpaEntity, UUID> {
    Optional<UserBookJpaEntity> findByUserIdAndBookId(UUID userId, UUID bookId);
    List<UserBookJpaEntity> findByUserId(UUID userId);
}