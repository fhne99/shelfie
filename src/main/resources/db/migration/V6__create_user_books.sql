CREATE TABLE user_books (
                            id          UUID            NOT NULL DEFAULT gen_random_uuid(),
                            user_id     UUID            NOT NULL,
                            book_id     UUID            NOT NULL,
                            status      reading_status  NOT NULL DEFAULT 'TO_READ',
                            is_public   BOOLEAN         NOT NULL DEFAULT FALSE,
                            rating      SMALLINT,
                            review      VARCHAR(2000),
                            added_at    TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
                            started_at  TIMESTAMPTZ,
                            finished_at TIMESTAMPTZ,

                            CONSTRAINT user_books_pk            PRIMARY KEY (id),
                            CONSTRAINT user_books_user_fk       FOREIGN KEY (user_id)
                                REFERENCES users(id) ON DELETE CASCADE,
                            CONSTRAINT user_books_book_fk       FOREIGN KEY (book_id)
                                REFERENCES books(id) ON DELETE RESTRICT,
                            CONSTRAINT user_books_unique        UNIQUE (user_id, book_id),
                            CONSTRAINT user_books_rating_ck     CHECK (rating BETWEEN 1 AND 5),
                            CONSTRAINT user_books_review_ck     CHECK (char_length(review) <= 2000),
                            CONSTRAINT user_books_started_ck    CHECK (
                                started_at IS NULL OR started_at >= added_at
                                ),
                            CONSTRAINT user_books_finished_ck   CHECK (
                                finished_at IS NULL OR
                                (started_at IS NOT NULL AND finished_at >= started_at)
                                ),
                            CONSTRAINT user_books_rating_status CHECK (
                                rating IS NULL OR status IN ('READ', 'READING', 'ABANDONED')
                                ),
                            CONSTRAINT user_books_to_read_private CHECK (
                                status != 'TO_READ' OR is_public = FALSE
)
    );

CREATE INDEX idx_user_books_user_id  ON user_books (user_id);
CREATE INDEX idx_user_books_status   ON user_books (user_id, status);
CREATE INDEX idx_user_books_public   ON user_books (user_id, is_public)
    WHERE is_public = TRUE;
CREATE INDEX idx_user_books_finished ON user_books (user_id, finished_at)
    WHERE finished_at IS NOT NULL;