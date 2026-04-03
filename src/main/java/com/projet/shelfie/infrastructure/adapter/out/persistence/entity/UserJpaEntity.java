package com.projet.shelfie.infrastructure.adapter.out.persistence.entity;

import com.projet.shelfie.domain.model.User;
import com.projet.shelfie.domain.model.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String pseudo;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "preferred_language")
    private String preferredLanguage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "is_private", nullable = false)
    private boolean isPrivate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public static UserJpaEntity fromDomain(User user) {
        var entity = new UserJpaEntity();
        entity.id = user.id();
        entity.email = user.email();
        entity.pseudo = user.pseudo();
        entity.passwordHash = user.passwordHash();
        entity.avatarUrl = user.avatarUrl();
        entity.preferredLanguage = user.preferredLanguage();
        entity.role = user.role();
        entity.isActive = user.isActive();
        entity.isPrivate = user.isPrivate();
        entity.createdAt = user.createdAt();
        entity.updatedAt = user.updatedAt();
        return entity;
    }

    public User toDomain() {
        return new User(
                id, email, pseudo, passwordHash, avatarUrl,
                preferredLanguage, role, isActive, isPrivate,
                createdAt, updatedAt
        );
    }
}