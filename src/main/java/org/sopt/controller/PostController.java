package org.sopt.controller;


import org.sopt.domain.Post;
import org.sopt.dto.PostRequest;
import org.sopt.dto.PostResponse;
import org.sopt.dto.PostUpdateRequest;
import org.sopt.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping("/post")
    public void createPost(@RequestBody final PostRequest postRequest) {
        postService.createPost(postRequest.title());
    }

    @GetMapping("/post")
    public List<PostResponse> getAllPosts(){
        return postService.getAllPost();
    }


    @GetMapping("/post/{postId}")
    public PostResponse getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @DeleteMapping("/post/{postId}")
    public void deletePostById(@PathVariable Long postId) {
        postService.deletePostById(postId);
    }

    @PatchMapping("/post")
    public void updatePostTitle(@RequestBody final PostUpdateRequest postUpdateRequest) {
        postService.updatePostTitle(postUpdateRequest.id(),postUpdateRequest.newTitle());
    }

    @GetMapping("/post/search")
    public List<PostResponse> searchPostsByKeyword(@RequestParam String keyword){
        return postService.searchPostsByTitle(keyword);
    }

}
