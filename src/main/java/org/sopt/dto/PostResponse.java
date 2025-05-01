package org.sopt.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponse(Long id, String title, List<String> photoUrls, LocalDateTime createdAt) {
}
