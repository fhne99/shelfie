package com.projet.shelfie.infrastructure.adapter.in.web.dto.response;

public record LoginResponse(
        String accessToken,
        long expiresIn
) {}