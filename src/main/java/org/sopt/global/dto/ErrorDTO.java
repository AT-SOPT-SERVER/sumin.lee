package org.sopt.global.dto;

public record ErrorDTO(
        String message
) {
    public static ErrorDTO of(String message) {
        return new ErrorDTO(message);
    }
}