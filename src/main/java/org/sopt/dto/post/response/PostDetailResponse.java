package org.sopt.dto.post.response;

import org.sopt.domain.post.Tag;

public record PostDetailResponse(String title, String content, Tag tag, String userName) {
}
