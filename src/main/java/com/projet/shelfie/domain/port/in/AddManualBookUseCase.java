package com.projet.shelfie.domain.port.in;

import com.projet.shelfie.domain.model.UserBook;
import java.util.UUID;

public interface AddManualBookUseCase {
    AddManualBookResult addManual(AddManualBookCommand command);

    record AddManualBookCommand(
            UUID userId,
            String title,
            String author,
            String isbn,
            String summary,
            String genre,
            Integer publishedYear
    ) {}

    record AddManualBookResult(UserBook userBook, boolean duplicateWarning) {}
}