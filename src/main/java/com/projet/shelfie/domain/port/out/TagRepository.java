package com.projet.shelfie.domain.port.out;

import com.projet.shelfie.domain.model.Tag;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TagRepository {
    Optional<Tag> findById(UUID id);
    List<Tag> findByUserId(UUID userId);
    boolean existsByUserIdAndName(UUID userId, String name);
    Tag save(Tag tag);
    void deleteById(UUID id);
}