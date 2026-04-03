package com.projet.shelfie.domain.model;

import java.time.Instant;
import java.util.UUID;

public record UserBook(
        UUID id,
        UUID userId,
        UUID bookId,
        ReadingStatus status,
        boolean isPublic,
        Integer rating,
        String review,
        Instant addedAt,
        Instant startedAt,
        Instant finishedAt
) {}