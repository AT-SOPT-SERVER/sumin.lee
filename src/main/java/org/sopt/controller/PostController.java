package org.sopt.controller;


import org.sopt.domain.Post;
import org.sopt.service.PostService;

import java.util.List;

public class PostController {
    private PostService postService = new PostService();
    private int postId;

    public void createPost(String title){
        if (title == null ||  title.length()>30){
            throw new IllegalArgumentException("제목은 1자 이상 30자 이하여야함");
        }
        Post post = new Post(postId++,title);
        postService.createPost(post);
    }

    public List<Post> getAllPosts(){
        return postService.getAllPost();
    }

    public Post getPostById(int id) {
        return postService.getPostById(id);
    }

    public boolean deletePostById(int id) {
        return postService.deletePostById(id);
    }
    public boolean updatePostTitle(int id,String newTitle) {
        if (newTitle == null ||  newTitle.length()>30){
            throw new IllegalArgumentException("제목은 1자 이상 30자 이하여야함");
        }
        return postService.updatePostById(id,newTitle);
    }

}
