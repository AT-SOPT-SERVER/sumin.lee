package org.sopt.dto;

import java.time.LocalDateTime;

public record PostResponse(Long id, String title, LocalDateTime createdAt) {
}
