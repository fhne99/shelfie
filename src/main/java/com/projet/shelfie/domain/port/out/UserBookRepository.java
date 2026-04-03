package com.projet.shelfie.domain.port.out;

import com.projet.shelfie.domain.model.UserBook;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserBookRepository {
    Optional<UserBook> findById(UUID id);
    Optional<UserBook> findByUserIdAndBookId(UUID userId, UUID bookId);
    List<UserBook> findByUserId(UUID userId);
    UserBook save(UserBook userBook);
    void deleteById(UUID id);
}