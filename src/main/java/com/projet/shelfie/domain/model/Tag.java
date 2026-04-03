package com.projet.shelfie.domain.model;

import java.time.Instant;
import java.util.UUID;

public record Tag(
        UUID id,
        UUID userId,
        String name,
        Instant createdAt
) {}