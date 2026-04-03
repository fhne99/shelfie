package com.projet.shelfie.domain.model;

import java.time.Instant;
import java.util.UUID;

public record User(
        UUID id,
        String email,
        String pseudo,
        String passwordHash,
        String avatarUrl,
        String preferredLanguage,
        UserRole role,
        boolean isActive,
        boolean isPrivate,
        Instant createdAt,
        Instant updatedAt
) {}