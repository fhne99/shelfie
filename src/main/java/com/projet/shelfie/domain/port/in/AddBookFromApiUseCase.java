package com.projet.shelfie.domain.port.in;

import com.projet.shelfie.domain.model.UserBook;
import java.util.UUID;

public interface AddBookFromApiUseCase {
    UserBook addFromApi(AddBookFromApiCommand command);

    record AddBookFromApiCommand(UUID userId, String googleBooksId) {}
}