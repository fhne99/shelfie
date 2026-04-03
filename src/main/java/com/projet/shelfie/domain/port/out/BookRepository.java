package com.projet.shelfie.domain.port.out;

import com.projet.shelfie.domain.model.Book;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository {
    Optional<Book> findById(UUID id);
    Optional<Book> findByGoogleBooksId(String googleBooksId);
    Optional<Book> findByIsbn(String isbn);
    Book save(Book book);
}