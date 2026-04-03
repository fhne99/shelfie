package com.projet.shelfie.infrastructure.adapter.out.persistence.adapter;

import com.projet.shelfie.domain.model.Tag;
import com.projet.shelfie.domain.port.out.TagRepository;
import com.projet.shelfie.infrastructure.adapter.out.persistence.repository.TagJpaRepository;
import com.projet.shelfie.infrastructure.adapter.out.persistence.entity.TagJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TagRepositoryAdapter implements TagRepository {

    private final TagJpaRepository jpaRepository;

    @Override
    public Optional<Tag> findById(UUID id) {
        return jpaRepository.findById(id).map(TagJpaEntity::toDomain);
    }

    @Override
    public List<Tag> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(TagJpaEntity::toDomain)
                .toList();
    }

    @Override
    public boolean existsByUserIdAndName(UUID userId, String name) {
        return jpaRepository.existsByUserIdAndName(userId, name);
    }

    @Override
    public Tag save(Tag tag) {
        return jpaRepository.save(TagJpaEntity.fromDomain(tag)).toDomain();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}