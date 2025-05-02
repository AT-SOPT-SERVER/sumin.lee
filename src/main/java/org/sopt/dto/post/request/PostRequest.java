package org.sopt.dto.post.request;

import org.sopt.domain.post.Tag;

public record PostRequest (String title, String content, Tag tag){
}
