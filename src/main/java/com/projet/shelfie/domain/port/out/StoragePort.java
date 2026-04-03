package com.projet.shelfie.domain.port.out;

public interface StoragePort {
    String uploadAvatar(byte[] imageBytes, String filename);
    void deleteAvatar(String avatarUrl);
}