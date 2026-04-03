package com.projet.shelfie.application.usecase;

import com.projet.shelfie.domain.exception.ResourceNotFoundException;
import com.projet.shelfie.domain.exception.UnauthorizedException;
import com.projet.shelfie.domain.model.ReadingStatus;
import com.projet.shelfie.domain.model.UserBook;
import com.projet.shelfie.domain.port.in.UpdateReadingStatusUseCase;
import com.projet.shelfie.domain.port.out.UserBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UpdateReadingStatusUseCaseImpl implements UpdateReadingStatusUseCase {

    private final UserBookRepository userBookRepository;

    @Override
    public UserBook updateStatus(UpdateStatusCommand command) {
        var userBook = userBookRepository.findById(command.userBookId())
                .orElseThrow(() -> new ResourceNotFoundException("UserBook", command.userBookId().toString()));

        if (!userBook.userId().equals(command.userId())) {
            throw new UnauthorizedException("Not your book");
        }

        var now = Instant.now();
        var startedAt = command.newStatus() == ReadingStatus.READING && userBook.startedAt() == null
                ? now : userBook.startedAt();
        var finishedAt = command.newStatus() == ReadingStatus.READ && userBook.finishedAt() == null
                ? now : userBook.finishedAt();
        var isPublic = command.newStatus() != ReadingStatus.TO_READ;

        var updated = new UserBook(
                userBook.id(),
                userBook.userId(),
                userBook.bookId(),
                command.newStatus(),
                isPublic,
                userBook.rating(),
                userBook.review(),
                userBook.addedAt(),
                startedAt,
                finishedAt
        );

        return userBookRepository.save(updated);
    }
}