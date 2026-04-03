package com.projet.shelfie.infrastructure.adapter.out.googlebooks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.shelfie.domain.model.Book;
import com.projet.shelfie.domain.model.BookSource;
import com.projet.shelfie.domain.port.out.BookMetadataPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class GoogleBooksAdapter implements BookMetadataPort {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${shelfie.google-books.api-key:}")
    private String apiKey;

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";

    public GoogleBooksAdapter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    @Override
    public List<Book> search(String query) {
        try {
            var url = BASE_URL + "?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8)
                    + "&maxResults=10"
                    + (apiKey.isBlank() ? "" : "&key=" + apiKey);

            var request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(5))
                    .GET()
                    .build();

            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return List.of();
            }

            return parseBooks(objectMapper.readTree(response.body()));

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return List.of();
        }
    }

    @Override
    public Optional<Book> findByGoogleBooksId(String googleBooksId) {
        try {
            var url = BASE_URL + "/" + googleBooksId
                    + (apiKey.isBlank() ? "" : "?key=" + apiKey);

            var request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(5))
                    .GET()
                    .build();

            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return Optional.empty();
            }

            return Optional.of(parseBook(googleBooksId, objectMapper.readTree(response.body())));

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    private List<Book> parseBooks(JsonNode root) {
        var books = new ArrayList<Book>();
        var items = root.path("items");
        if (items.isMissingNode()) return books;

        items.forEach(item -> {
            var id = item.path("id").asText();
            books.add(parseBook(id, item));
        });

        return books;
    }

    private Book parseBook(String googleBooksId, JsonNode item) {
        var info = item.path("volumeInfo");
        var isbn = extractIsbn(info);
        var authors = info.path("authors");
        var author = authors.isArray() && authors.size() > 0
                ? authors.get(0).asText() : null;
        var thumbnail = info.path("imageLinks").path("thumbnail").asText(null);
        var publishedYear = extractYear(info.path("publishedDate").asText(null));

        return new Book(
                UUID.randomUUID(),
                googleBooksId,
                isbn,
                info.path("title").asText("Unknown title"),
                author,
                thumbnail,
                info.path("description").asText(null),
                extractGenre(info),
                publishedYear,
                info.path("pageCount").asInt(0) > 0
                        ? info.path("pageCount").asInt() : null,
                info.path("language").asText(null),
                BookSource.GOOGLE_BOOKS,
                Instant.now()
        );
    }

    private String extractIsbn(JsonNode info) {
        var identifiers = info.path("industryIdentifiers");
        if (!identifiers.isArray()) return null;
        for (var id : identifiers) {
            if ("ISBN_13".equals(id.path("type").asText())) {
                return id.path("identifier").asText(null);
            }
        }
        return null;
    }

    private String extractGenre(JsonNode info) {
        var categories = info.path("categories");
        if (categories.isArray() && categories.size() > 0) {
            return categories.get(0).asText(null);
        }
        return null;
    }

    private Integer extractYear(String publishedDate) {
        if (publishedDate == null || publishedDate.length() < 4) return null;
        try {
            return Integer.parseInt(publishedDate.substring(0, 4));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}