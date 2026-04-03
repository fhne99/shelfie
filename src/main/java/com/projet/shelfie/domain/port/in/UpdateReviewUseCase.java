package com.projet.shelfie.domain.port.in;

import com.projet.shelfie.domain.model.UserBook;
import java.util.UUID;

public interface UpdateReviewUseCase {
    UserBook updateReview(UpdateReviewCommand command);

    record UpdateReviewCommand(
            UUID userId,
            UUID userBookId,
            Integer rating,
            String review
    ) {}
}