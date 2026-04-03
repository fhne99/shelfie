package com.projet.shelfie.infrastructure.adapter.out.persistence.entity;

import com.projet.shelfie.domain.model.Book;
import com.projet.shelfie.domain.model.BookSource;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class BookJpaEntity {

    @Id
    private UUID id;

    @Column(name = "google_books_id", unique = true)
    private String googleBooksId;

    @Column(unique = true)
    private String isbn;

    @Column(nullable = false)
    private String title;

    private String author;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private String genre;

    @Column(name = "published_year")
    private Integer publishedYear;

    @Column(name = "page_count")
    private Integer pageCount;

    private String language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookSource source;

    @Column(name = "cached_at", nullable = false)
    private Instant cachedAt;

    public static BookJpaEntity fromDomain(Book book) {
        var entity = new BookJpaEntity();
        entity.id = book.id();
        entity.googleBooksId = book.googleBooksId();
        entity.isbn = book.isbn();
        entity.title = book.title();
        entity.author = book.author();
        entity.thumbnailUrl = book.thumbnailUrl();
        entity.summary = book.summary();
        entity.genre = book.genre();
        entity.publishedYear = book.publishedYear();
        entity.pageCount = book.pageCount();
        entity.language = book.language();
        entity.source = book.source();
        entity.cachedAt = book.cachedAt();
        return entity;
    }

    public Book toDomain() {
        return new Book(
                id, googleBooksId, isbn, title, author,
                thumbnailUrl, summary, genre, publishedYear,
                pageCount, language, source, cachedAt
        );
    }
}