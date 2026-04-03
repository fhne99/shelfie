package com.projet.shelfie.domain.exception;

public abstract class ShelfieException extends RuntimeException {
    protected ShelfieException(String message) {
        super(message);
    }
}