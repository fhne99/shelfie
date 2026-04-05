CREATE TABLE follows (
                         id          UUID        NOT NULL DEFAULT gen_random_uuid(),
                         follower_id UUID        NOT NULL,
                         followee_id UUID        NOT NULL,
                         created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                         CONSTRAINT follows_pk          PRIMARY KEY (id),
                         CONSTRAINT follows_unique      UNIQUE (follower_id, followee_id),
                         CONSTRAINT follows_no_self     CHECK (follower_id != followee_id),
    CONSTRAINT follows_follower_fk FOREIGN KEY (follower_id)
                                       REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT follows_followee_fk FOREIGN KEY (followee_id)
                                       REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_follows_follower ON follows (follower_id);
CREATE INDEX idx_follows_followee ON follows (followee_id);