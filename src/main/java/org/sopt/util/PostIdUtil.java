package org.sopt.util;

import org.sopt.domain.Post;

import java.util.List;

public class PostIdUtil {

    public static int generateNewId(List<Post> postList){
        return postList.stream().mapToInt(Post::getId).max().orElse(0) + 1;
    }
}
