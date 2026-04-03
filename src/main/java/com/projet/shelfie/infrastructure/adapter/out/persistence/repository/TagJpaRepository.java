package com.projet.shelfie.infrastructure.adapter.out.persistence.repository;

import com.projet.shelfie.infrastructure.adapter.out.persistence.entity.TagJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TagJpaRepository extends JpaRepository<TagJpaEntity, UUID> {
    List<TagJpaEntity> findByUserId(UUID userId);
    boolean existsByUserIdAndName(UUID userId, String name);
}