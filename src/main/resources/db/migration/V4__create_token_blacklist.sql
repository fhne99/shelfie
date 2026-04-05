CREATE TABLE token_blacklist (
                                 id          UUID        NOT NULL DEFAULT gen_random_uuid(),
                                 jti         VARCHAR(36) NOT NULL,
                                 expires_at  TIMESTAMPTZ NOT NULL,
                                 created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                                 CONSTRAINT token_blacklist_pk     PRIMARY KEY (id),
                                 CONSTRAINT token_blacklist_jti_uk UNIQUE (jti)
);

CREATE INDEX idx_token_blacklist_jti     ON token_blacklist (jti);
CREATE INDEX idx_token_blacklist_expires ON token_blacklist (expires_at);