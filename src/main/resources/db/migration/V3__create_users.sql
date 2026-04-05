CREATE TABLE users (
                       id                  UUID            NOT NULL DEFAULT gen_random_uuid(),
                       email               VARCHAR(255)    NOT NULL,
                       pseudo              VARCHAR(50)     NOT NULL,
                       password_hash       VARCHAR(255)    NOT NULL,
                       avatar_url          VARCHAR(512),
                       preferred_language  VARCHAR(10),
                       role                user_role       NOT NULL DEFAULT 'ROLE_USER',
                       is_active           BOOLEAN         NOT NULL DEFAULT FALSE,
                       is_private          BOOLEAN         NOT NULL DEFAULT FALSE,
                       created_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
                       updated_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),

                       CONSTRAINT users_pk             PRIMARY KEY (id),
                       CONSTRAINT users_email_uk       UNIQUE (email),
                       CONSTRAINT users_pseudo_uk      UNIQUE (pseudo),
                       CONSTRAINT users_pseudo_length  CHECK (char_length(pseudo) BETWEEN 3 AND 50)
);

CREATE INDEX idx_users_email       ON users (email);
CREATE INDEX idx_users_pseudo      ON users (pseudo);
CREATE INDEX idx_users_pseudo_trgm ON users USING GIN (pseudo gin_trgm_ops);