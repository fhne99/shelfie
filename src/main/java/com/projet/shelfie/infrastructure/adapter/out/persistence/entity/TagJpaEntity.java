package com.projet.shelfie.infrastructure.adapter.out.persistence.entity;

import com.projet.shelfie.domain.model.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
public class TagJpaEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public static TagJpaEntity fromDomain(Tag tag) {
        var entity = new TagJpaEntity();
        entity.id = tag.id();
        entity.userId = tag.userId();
        entity.name = tag.name();
        entity.createdAt = tag.createdAt();
        return entity;
    }

    public Tag toDomain() {
        return new Tag(id, userId, name, createdAt);
    }
}