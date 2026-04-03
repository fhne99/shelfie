package com.projet.shelfie.domain.exception;

public class PseudoAlreadyExistsException extends ShelfieException {
    public PseudoAlreadyExistsException(String pseudo) {
        super("Pseudo already exists: " + pseudo);
    }
}