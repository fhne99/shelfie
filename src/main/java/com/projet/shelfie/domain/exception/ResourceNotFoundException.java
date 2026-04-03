package com.projet.shelfie.domain.exception;

public class ResourceNotFoundException extends ShelfieException {
    public ResourceNotFoundException(String resource, String id) {
        super(resource + " not found: " + id);
    }
}