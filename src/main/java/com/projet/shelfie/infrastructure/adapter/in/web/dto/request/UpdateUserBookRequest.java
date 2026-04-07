package com.projet.shelfie.infrastructure.adapter.in.web.dto.request;

public record UpdateUserBookRequest(
        Action action,
        String status,
        Integer rating,
        String review
) {
    public enum Action { STATUS, REVIEW }
}