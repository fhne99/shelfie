package com.projet.shelfie.domain.exception;

public class UnauthorizedException extends ShelfieException {
    public UnauthorizedException(String message) {
        super(message);
    }
}