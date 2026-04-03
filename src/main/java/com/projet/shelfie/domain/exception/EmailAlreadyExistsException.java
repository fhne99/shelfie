package com.projet.shelfie.domain.exception;

public class EmailAlreadyExistsException extends ShelfieException {
    public EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email);
    }
}