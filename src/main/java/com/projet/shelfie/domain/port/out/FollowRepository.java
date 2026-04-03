package com.projet.shelfie.domain.port.out;

import com.projet.shelfie.domain.model.Follow;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FollowRepository {
    Optional<Follow> findByFollowerIdAndFolloweeId(UUID followerId, UUID followeeId);
    List<Follow> findByFollowerId(UUID followerId);
    List<Follow> findByFolloweeId(UUID followeeId);
    boolean existsByFollowerIdAndFolloweeId(UUID followerId, UUID followeeId);
    Follow save(Follow follow);
    void delete(Follow follow);
}