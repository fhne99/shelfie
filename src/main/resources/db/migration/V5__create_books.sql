CREATE TABLE books (
                       id                  UUID            NOT NULL DEFAULT gen_random_uuid(),
                       google_books_id     VARCHAR(20),
                       isbn                VARCHAR(13),
                       title               VARCHAR(500)    NOT NULL,
                       author              VARCHAR(300),
                       thumbnail_url       VARCHAR(512),
                       summary             TEXT,
                       genre               VARCHAR(100),
                       published_year      SMALLINT,
                       page_count          SMALLINT,
                       language            VARCHAR(10),
                       source              book_source     NOT NULL DEFAULT 'GOOGLE_BOOKS',
                       cached_at           TIMESTAMPTZ     NOT NULL DEFAULT NOW(),

                       CONSTRAINT books_pk              PRIMARY KEY (id),
                       CONSTRAINT books_google_id_uk    UNIQUE (google_books_id),
                       CONSTRAINT books_isbn_uk         UNIQUE (isbn),
                       CONSTRAINT books_title_not_empty CHECK (char_length(trim(title)) > 0),
                       CONSTRAINT books_google_required CHECK (
                           source = 'MANUAL' OR google_books_id IS NOT NULL
                           )
);

CREATE INDEX idx_books_isbn        ON books (isbn)            WHERE isbn IS NOT NULL;
CREATE INDEX idx_books_google_id   ON books (google_books_id) WHERE google_books_id IS NOT NULL;
CREATE INDEX idx_books_title_trgm  ON books USING GIN (title gin_trgm_ops);
CREATE INDEX idx_books_author_trgm ON books USING GIN (author gin_trgm_ops);