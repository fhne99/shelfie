package com.projet.shelfie.infrastructure.adapter.out.persistence.repository;

import com.projet.shelfie.infrastructure.adapter.out.persistence.entity.FollowJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FollowJpaRepository extends JpaRepository<FollowJpaEntity, UUID> {
    Optional<FollowJpaEntity> findByFollowerIdAndFolloweeId(UUID followerId, UUID followeeId);
    List<FollowJpaEntity> findByFollowerId(UUID followerId);
    List<FollowJpaEntity> findByFolloweeId(UUID followeeId);
    boolean existsByFollowerIdAndFolloweeId(UUID followerId, UUID followeeId);
}