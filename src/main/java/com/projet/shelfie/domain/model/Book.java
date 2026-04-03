package com.projet.shelfie.domain.model;

import java.time.Instant;
import java.util.UUID;

public record Book(
        UUID id,
        String googleBooksId,
        String isbn,
        String title,
        String author,
        String thumbnailUrl,
        String summary,
        String genre,
        Integer publishedYear,
        Integer pageCount,
        String language,
        BookSource source,
        Instant cachedAt
) {}