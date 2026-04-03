package com.projet.shelfie.domain.model;

import java.time.Instant;
import java.util.UUID;

public record Follow(
        UUID id,
        UUID followerId,
        UUID followeeId,
        Instant createdAt
) {}