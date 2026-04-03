package com.projet.shelfie.infrastructure.adapter.out.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.projet.shelfie.domain.port.out.StoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CloudinaryStorageAdapter implements StoragePort {

    private final Cloudinary cloudinary;

    @Override
    public String uploadAvatar(byte[] imageBytes, String filename) {
        try {
            Map result = cloudinary.uploader().upload(imageBytes, ObjectUtils.asMap(
                    "public_id", "avatars/" + filename,
                    "transformation", "w_200,h_200,c_fill,f_webp",
                    "overwrite", true
            ));
            return (String) result.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload avatar to Cloudinary", e);
        }
    }

    @Override
    public void deleteAvatar(String avatarUrl) {
        try {
            var publicId = extractPublicId(avatarUrl);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete avatar from Cloudinary", e);
        }
    }

    private String extractPublicId(String url) {
        // Ex : https://res.cloudinary.com/xxx/image/upload/v123/avatars/user123.webp
        // → avatars/user123
        var parts = url.split("/upload/");
        if (parts.length < 2) throw new RuntimeException("Invalid Cloudinary URL: " + url);
        var withVersion = parts[1];
        var withoutVersion = withVersion.replaceFirst("v\\d+/", "");
        return withoutVersion.replaceFirst("\\.[^.]+$", "");
    }
}