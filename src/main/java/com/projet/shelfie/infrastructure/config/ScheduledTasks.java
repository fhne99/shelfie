package com.projet.shelfie.infrastructure.config;

import com.projet.shelfie.domain.port.out.TokenBlacklistPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {

    private final TokenBlacklistPort tokenBlacklistPort;

    @Scheduled(cron = "0 0 2 * * *") // chaque nuit à 2h
    public void cleanExpiredTokens() {
        log.info("Cleaning expired blacklisted tokens...");
        tokenBlacklistPort.deleteExpired();
        log.info("Expired tokens cleaned.");
    }
}