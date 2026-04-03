package com.projet.shelfie.infrastructure.adapter.out.persistence.adapter;

import com.projet.shelfie.domain.model.Follow;
import com.projet.shelfie.domain.port.out.FollowRepository;
import com.projet.shelfie.infrastructure.adapter.out.persistence.entity.FollowJpaEntity;
import com.projet.shelfie.infrastructure.adapter.out.persistence.repository.FollowJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FollowRepositoryAdapter implements FollowRepository {

    private final FollowJpaRepository jpaRepository;

    @Override
    public Optional<Follow> findByFollowerIdAndFolloweeId(UUID followerId, UUID followeeId) {
        return jpaRepository.findByFollowerIdAndFolloweeId(followerId, followeeId)
                .map(FollowJpaEntity::toDomain);
    }

    @Override
    public List<Follow> findByFollowerId(UUID followerId) {
        return jpaRepository.findByFollowerId(followerId).stream()
                .map(FollowJpaEntity::toDomain)
                .toList();
    }

    @Override
    public List<Follow> findByFolloweeId(UUID followeeId) {
        return jpaRepository.findByFolloweeId(followeeId).stream()
                .map(FollowJpaEntity::toDomain)
                .toList();
    }

    @Override
    public boolean existsByFollowerIdAndFolloweeId(UUID followerId, UUID followeeId) {
        return jpaRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId);
    }

    @Override
    public Follow save(Follow follow) {
        return jpaRepository.save(FollowJpaEntity.fromDomain(follow)).toDomain();
    }

    @Override
    public void delete(Follow follow) {
        jpaRepository.delete(FollowJpaEntity.fromDomain(follow));
    }
}