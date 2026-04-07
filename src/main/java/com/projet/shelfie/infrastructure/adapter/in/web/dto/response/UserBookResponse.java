package com.projet.shelfie.infrastructure.adapter.in.web.dto.response;

import com.projet.shelfie.domain.model.ReadingStatus;
import com.projet.shelfie.domain.model.UserBook;

import java.time.Instant;
import java.util.UUID;

public record UserBookResponse(
        UUID id,
        UUID bookId,
        ReadingStatus status,
        boolean isPublic,
        Integer rating,
        String review,
        Instant addedAt,
        Instant startedAt,
        Instant finishedAt
) {
    public static UserBookResponse fromDomain(UserBook userBook) {
        return new UserBookResponse(
                userBook.id(),
                userBook.bookId(),
                userBook.status(),
                userBook.isPublic(),
                userBook.rating(),
                userBook.review(),
                userBook.addedAt(),
                userBook.startedAt(),
                userBook.finishedAt()
        );
    }
}