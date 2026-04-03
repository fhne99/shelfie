package com.projet.shelfie.domain.port.out;

import com.projet.shelfie.domain.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookMetadataPort {
    List<Book> search(String query);
    Optional<Book> findByGoogleBooksId(String googleBooksId);
}