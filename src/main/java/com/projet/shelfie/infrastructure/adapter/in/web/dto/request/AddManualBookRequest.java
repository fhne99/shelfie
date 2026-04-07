package com.projet.shelfie.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddManualBookRequest(
        @NotBlank @Size(max = 500) String title,
        String author,
        String isbn,
        String summary,
        String genre,
        Integer publishedYear
) {}