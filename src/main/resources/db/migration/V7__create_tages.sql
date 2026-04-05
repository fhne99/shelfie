CREATE TABLE tags (
                      id          UUID        NOT NULL DEFAULT gen_random_uuid(),
                      user_id     UUID        NOT NULL,
                      name        VARCHAR(50) NOT NULL,
                      created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                      CONSTRAINT tags_pk      PRIMARY KEY (id),
                      CONSTRAINT tags_user_fk FOREIGN KEY (user_id)
                          REFERENCES users(id) ON DELETE CASCADE,
                      CONSTRAINT tags_unique  UNIQUE (user_id, name),
                      CONSTRAINT tags_name_ck CHECK (char_length(trim(name)) BETWEEN 1 AND 50)
);

CREATE INDEX idx_tags_user_id ON tags (user_id);

CREATE TABLE book_tags (
                           user_book_id UUID NOT NULL,
                           tag_id       UUID NOT NULL,

                           CONSTRAINT book_tags_pk           PRIMARY KEY (user_book_id, tag_id),
                           CONSTRAINT book_tags_user_book_fk FOREIGN KEY (user_book_id)
                               REFERENCES user_books(id) ON DELETE CASCADE,
                           CONSTRAINT book_tags_tag_fk       FOREIGN KEY (tag_id)
                               REFERENCES tags(id) ON DELETE CASCADE
);

CREATE INDEX idx_book_tags_tag_id ON book_tags (tag_id);