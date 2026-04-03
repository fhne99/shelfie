package com.projet.shelfie.infrastructure.adapter.out.persistence.adapter;

import com.projet.shelfie.domain.model.UserBook;
import com.projet.shelfie.domain.port.out.UserBookRepository;
import com.projet.shelfie.infrastructure.adapter.out.persistence.entity.UserBookJpaEntity;
import com.projet.shelfie.infrastructure.adapter.out.persistence.repository.UserBookJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserBookRepositoryAdapter implements UserBookRepository {

    private final UserBookJpaRepository jpaRepository;

    @Override
    public Optional<UserBook> findById(UUID id) {
        return jpaRepository.findById(id).map(UserBookJpaEntity::toDomain);
    }

    @Override
    public Optional<UserBook> findByUserIdAndBookId(UUID userId, UUID bookId) {
        return jpaRepository.findByUserIdAndBookId(userId, bookId)
                .map(UserBookJpaEntity::toDomain);
    }

    @Override
    public List<UserBook> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(UserBookJpaEntity::toDomain)
                .toList();
    }

    @Override
    public UserBook save(UserBook userBook) {
        return jpaRepository.save(UserBookJpaEntity.fromDomain(userBook)).toDomain();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}