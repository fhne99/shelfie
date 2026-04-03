package com.projet.shelfie.application.usecase;

import com.projet.shelfie.domain.exception.ResourceNotFoundException;
import com.projet.shelfie.domain.exception.UnauthorizedException;
import com.projet.shelfie.domain.model.ReadingStatus;
import com.projet.shelfie.domain.model.UserBook;
import com.projet.shelfie.domain.port.in.UpdateReviewUseCase;
import com.projet.shelfie.domain.port.out.UserBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateReviewUseCaseImpl implements UpdateReviewUseCase {

    private final UserBookRepository userBookRepository;

    @Override
    public UserBook updateReview(UpdateReviewCommand command) {
        var userBook = userBookRepository.findById(command.userBookId())
                .orElseThrow(() -> new ResourceNotFoundException("UserBook", command.userBookId().toString()));

        if (!userBook.userId().equals(command.userId())) {
            throw new UnauthorizedException("Not your book");
        }

        if (userBook.status() == ReadingStatus.TO_READ) {
            throw new UnauthorizedException("Cannot review a book with status TO_READ");
        }

        var updated = new UserBook(
                userBook.id(),
                userBook.userId(),
                userBook.bookId(),
                userBook.status(),
                userBook.isPublic(),
                command.rating() != null ? command.rating() : userBook.rating(),
                command.review() != null ? command.review() : userBook.review(),
                userBook.addedAt(),
                userBook.startedAt(),
                userBook.finishedAt()
        );

        return userBookRepository.save(updated);
    }
}