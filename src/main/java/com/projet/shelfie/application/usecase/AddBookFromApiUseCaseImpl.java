package com.projet.shelfie.application.usecase;

import com.projet.shelfie.domain.exception.ResourceNotFoundException;
import com.projet.shelfie.domain.exception.UnauthorizedException;
import com.projet.shelfie.domain.model.ReadingStatus;
import com.projet.shelfie.domain.model.UserBook;
import com.projet.shelfie.domain.port.in.AddBookFromApiUseCase;
import com.projet.shelfie.domain.port.out.BookMetadataPort;
import com.projet.shelfie.domain.port.out.BookRepository;
import com.projet.shelfie.domain.port.out.UserBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddBookFromApiUseCaseImpl implements AddBookFromApiUseCase {

    private final BookMetadataPort bookMetadataPort;
    private final BookRepository bookRepository;
    private final UserBookRepository userBookRepository;

    @Override
    public UserBook addFromApi(AddBookFromApiCommand command) {
        // Vérifier que le livre n'est pas déjà dans la bibliothèque
        var existingBook = bookRepository.findByGoogleBooksId(command.googleBooksId());

        var book = existingBook.orElseGet(() ->
                bookMetadataPort.findByGoogleBooksId(command.googleBooksId())
                        .map(bookRepository::save)
                        .orElseThrow(() -> new ResourceNotFoundException("Book", command.googleBooksId()))
        );

        // Vérifier le doublon dans la bibliothèque de l'utilisateur
        userBookRepository.findByUserIdAndBookId(command.userId(), book.id())
                .ifPresent(ub -> {
                    throw new UnauthorizedException("Book already in library");
                });

        var now = Instant.now();
        var userBook = new UserBook(
                UUID.randomUUID(),
                command.userId(),
                book.id(),
                ReadingStatus.TO_READ,
                false,
                null,
                null,
                now,
                null,
                null
        );

        return userBookRepository.save(userBook);
    }
}