package com.projet.shelfie.domain.port.in;

import com.projet.shelfie.domain.model.ReadingStatus;
import com.projet.shelfie.domain.model.UserBook;
import java.util.UUID;

public interface UpdateReadingStatusUseCase {
    UserBook updateStatus(UpdateStatusCommand command);

    record UpdateStatusCommand(UUID userId, UUID userBookId, ReadingStatus newStatus) {}
}