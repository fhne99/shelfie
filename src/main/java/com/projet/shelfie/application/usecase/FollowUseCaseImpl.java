package com.projet.shelfie.application.usecase;

import com.projet.shelfie.domain.exception.ResourceNotFoundException;
import com.projet.shelfie.domain.exception.UnauthorizedException;
import com.projet.shelfie.domain.model.Follow;
import com.projet.shelfie.domain.port.in.FollowUseCase;
import com.projet.shelfie.domain.port.out.FollowRepository;
import com.projet.shelfie.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FollowUseCaseImpl implements FollowUseCase {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Override
    public Follow follow(FollowCommand command) {
        var followee = userRepository.findByPseudo(command.followeePseudo())
                .orElseThrow(() -> new ResourceNotFoundException("User", command.followeePseudo()));

        if (followee.isPrivate()) {
            throw new UnauthorizedException("This profile is private");
        }

        if (followee.id().equals(command.followerId())) {
            throw new UnauthorizedException("Cannot follow yourself");
        }

        if (followRepository.existsByFollowerIdAndFolloweeId(command.followerId(), followee.id())) {
            throw new UnauthorizedException("Already following this user");
        }

        var follow = new Follow(
                UUID.randomUUID(),
                command.followerId(),
                followee.id(),
                Instant.now()
        );

        return followRepository.save(follow);
    }

    @Override
    public void unfollow(UnfollowCommand command) {
        var followee = userRepository.findByPseudo(command.followeePseudo())
                .orElseThrow(() -> new ResourceNotFoundException("User", command.followeePseudo()));

        var follow = followRepository.findByFollowerIdAndFolloweeId(
                        command.followerId(), followee.id())
                .orElseThrow(() -> new ResourceNotFoundException("Follow", command.followeePseudo()));

        followRepository.delete(follow);
    }
}