package com.projet.shelfie.infrastructure.adapter.out.persistence.entity;

import com.projet.shelfie.domain.model.ReadingStatus;
import com.projet.shelfie.domain.model.UserBook;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_books")
@Getter
@Setter
@NoArgsConstructor
public class UserBookJpaEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "book_id", nullable = false)
    private UUID bookId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReadingStatus status;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    private Integer rating;

    @Column(columnDefinition = "VARCHAR(2000)")
    private String review;

    @Column(name = "added_at", nullable = false)
    private Instant addedAt;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    public static UserBookJpaEntity fromDomain(UserBook userBook) {
        var entity = new UserBookJpaEntity();
        entity.id = userBook.id();
        entity.userId = userBook.userId();
        entity.bookId = userBook.bookId();
        entity.status = userBook.status();
        entity.isPublic = userBook.isPublic();
        entity.rating = userBook.rating();
        entity.review = userBook.review();
        entity.addedAt = userBook.addedAt();
        entity.startedAt = userBook.startedAt();
        entity.finishedAt = userBook.finishedAt();
        return entity;
    }

    public UserBook toDomain() {
        return new UserBook(
                id, userId, bookId, status, isPublic,
                rating, review, addedAt, startedAt, finishedAt
        );
    }
}