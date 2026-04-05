package com.projet.shelfie.infrastructure.adapter.in.web.dto.response;

import java.util.UUID;

public record RegisterResponse(
        UUID id,
        String email,
        String pseudo
) {}