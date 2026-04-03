package com.projet.shelfie.infrastructure.adapter.out.persistence.entity;

import com.projet.shelfie.domain.model.Follow;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "follows")
@Getter
@Setter
@NoArgsConstructor
public class FollowJpaEntity {

    @Id
    private UUID id;

    @Column(name = "follower_id", nullable = false)
    private UUID followerId;

    @Column(name = "followee_id", nullable = false)
    private UUID followeeId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public static FollowJpaEntity fromDomain(Follow follow) {
        var entity = new FollowJpaEntity();
        entity.id = follow.id();
        entity.followerId = follow.followerId();
        entity.followeeId = follow.followeeId();
        entity.createdAt = follow.createdAt();
        return entity;
    }

    public Follow toDomain() {
        return new Follow(id, followerId, followeeId, createdAt);
    }
}