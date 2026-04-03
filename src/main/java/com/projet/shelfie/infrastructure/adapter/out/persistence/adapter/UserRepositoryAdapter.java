package com.projet.shelfie.infrastructure.adapter.out.persistence.adapter;

import com.projet.shelfie.domain.model.User;
import com.projet.shelfie.domain.port.out.UserRepository;
import com.projet.shelfie.infrastructure.adapter.out.persistence.entity.UserJpaEntity;
import com.projet.shelfie.infrastructure.adapter.out.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).map(UserJpaEntity::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(UserJpaEntity::toDomain);
    }

    @Override
    public Optional<User> findByPseudo(String pseudo) {
        return jpaRepository.findByPseudo(pseudo).map(UserJpaEntity::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPseudo(String pseudo) {
        return jpaRepository.existsByPseudo(pseudo);
    }

    @Override
    public User save(User user) {
        return jpaRepository.save(UserJpaEntity.fromDomain(user)).toDomain();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}