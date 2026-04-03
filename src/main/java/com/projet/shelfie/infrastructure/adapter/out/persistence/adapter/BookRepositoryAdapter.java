package com.projet.shelfie.infrastructure.adapter.out.persistence.adapter;

import com.projet.shelfie.domain.model.Book;
import com.projet.shelfie.domain.port.out.BookRepository;
import com.projet.shelfie.infrastructure.adapter.out.persistence.entity.BookJpaEntity;
import com.projet.shelfie.infrastructure.adapter.out.persistence.repository.BookJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BookRepositoryAdapter implements BookRepository {

    private final BookJpaRepository jpaRepository;

    @Override
    public Optional<Book> findById(UUID id) {
        return jpaRepository.findById(id).map(BookJpaEntity::toDomain);
    }

    @Override
    public Optional<Book> findByGoogleBooksId(String googleBooksId) {
        return jpaRepository.findByGoogleBooksId(googleBooksId).map(BookJpaEntity::toDomain);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return jpaRepository.findByIsbn(isbn).map(BookJpaEntity::toDomain);
    }

    @Override
    public Book save(Book book) {
        return jpaRepository.save(BookJpaEntity.fromDomain(book)).toDomain();
    }
}