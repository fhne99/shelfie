package com.projet.shelfie.infrastructure.adapter.out.persistence.repository;

import com.projet.shelfie.infrastructure.adapter.out.persistence.entity.BookJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookJpaRepository extends JpaRepository<BookJpaEntity, UUID> {
    Optional<BookJpaEntity> findByGoogleBooksId(String googleBooksId);
    Optional<BookJpaEntity> findByIsbn(String isbn);
}