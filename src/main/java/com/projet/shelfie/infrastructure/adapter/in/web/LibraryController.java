package com.projet.shelfie.infrastructure.adapter.in.web;

import com.projet.shelfie.domain.model.ReadingStatus;
import com.projet.shelfie.domain.port.in.AddBookFromApiUseCase;
import com.projet.shelfie.domain.port.in.AddManualBookUseCase;
import com.projet.shelfie.domain.port.in.UpdateReadingStatusUseCase;
import com.projet.shelfie.domain.port.in.UpdateReviewUseCase;
import com.projet.shelfie.infrastructure.adapter.in.web.dto.request.AddBookFromApiRequest;
import com.projet.shelfie.infrastructure.adapter.in.web.dto.request.AddManualBookRequest;
import com.projet.shelfie.infrastructure.adapter.in.web.dto.request.UpdateUserBookRequest;
import com.projet.shelfie.infrastructure.adapter.in.web.dto.response.UserBookResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/me/library")
@RequiredArgsConstructor
public class LibraryController {

    private final AddBookFromApiUseCase addBookFromApiUseCase;
    private final AddManualBookUseCase addManualBookUseCase;
    private final UpdateReadingStatusUseCase updateReadingStatusUseCase;
    private final UpdateReviewUseCase updateReviewUseCase;

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public UserBookResponse addFromApi(
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody AddBookFromApiRequest request) {
        var userBook = addBookFromApiUseCase.addFromApi(
                new AddBookFromApiUseCase.AddBookFromApiCommand(
                        userId,
                        request.googleBooksId()
                )
        );
        return UserBookResponse.fromDomain(userBook);
    }

    @PostMapping("/books/manual")
    @ResponseStatus(HttpStatus.CREATED)
    public UserBookResponse addManual(
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody AddManualBookRequest request) {
        var result = addManualBookUseCase.addManual(
                new AddManualBookUseCase.AddManualBookCommand(
                        userId,
                        request.title(),
                        request.author(),
                        request.isbn(),
                        request.summary(),
                        request.genre(),
                        request.publishedYear()
                )
        );
        return UserBookResponse.fromDomain(result.userBook());
    }

    @PatchMapping("/books/{id}")
    public UserBookResponse updateUserBook(
            @AuthenticationPrincipal UUID userId,
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserBookRequest request) {

        var userBook = switch (request.action()) {
            case STATUS -> updateReadingStatusUseCase.updateStatus(
                    new UpdateReadingStatusUseCase.UpdateStatusCommand(
                            userId, id, ReadingStatus.valueOf(request.status())
                    )
            );
            case REVIEW -> updateReviewUseCase.updateReview(
                    new UpdateReviewUseCase.UpdateReviewCommand(
                            userId, id, request.rating(), request.review()
                    )
            );
        };
        return UserBookResponse.fromDomain(userBook);
    }
}