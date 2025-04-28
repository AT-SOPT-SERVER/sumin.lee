package org.sopt.controller;


import org.sopt.domain.Post;
import org.sopt.dto.PostRequest;
import org.sopt.dto.PostResponse;
import org.sopt.dto.PostUpdateRequest;
import org.sopt.global.dto.ResponseDTO;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping("/post")
    public ResponseEntity<ResponseDTO<Void>> createPost(@RequestBody final PostRequest postRequest) {
        postService.createPost(postRequest.title());

        return ResponseEntity.ok(ResponseDTO.success(null));
    }

    @GetMapping("/post")
    public ResponseEntity<ResponseDTO<List<PostResponse>>>getAllPosts(){

        List<PostResponse> results = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.success(results));
    }


    @GetMapping("/post/{postId}")
    public PostResponse getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @DeleteMapping("/post/{postId}")
    public void deletePostById(@PathVariable Long postId) {
        postService.deletePostById(postId);
    }

    @PatchMapping("/post/{postId}")
    public void updatePostTitle(@PathVariable Long postId ,@RequestBody final PostUpdateRequest postUpdateRequest) {
        postService.updatePostTitle(postId,postUpdateRequest.title());
    }

    @GetMapping("/post/search")
    public List<PostResponse> searchPostsByKeyword(@RequestParam String keyword){
        return postService.searchPostsByTitle(keyword);
    }

}
