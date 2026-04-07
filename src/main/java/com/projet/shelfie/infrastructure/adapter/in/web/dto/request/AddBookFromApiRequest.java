package com.projet.shelfie.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddBookFromApiRequest(
        @NotBlank String googleBooksId
) {}