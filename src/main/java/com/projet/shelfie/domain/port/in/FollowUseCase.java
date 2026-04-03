package com.projet.shelfie.domain.port.in;

import com.projet.shelfie.domain.model.Follow;
import java.util.UUID;

public interface FollowUseCase {
    Follow follow(FollowCommand command);
    void unfollow(UnfollowCommand command);

    record FollowCommand(UUID followerId, String followeePseudo) {}
    record UnfollowCommand(UUID followerId, String followeePseudo) {}
}