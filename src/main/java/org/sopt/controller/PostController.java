package org.sopt.controller;


import org.sopt.domain.post.Tag;
import org.sopt.dto.post.request.PostRequest;
import org.sopt.dto.post.response.PostDetailResponse;
import org.sopt.dto.post.response.PostResponse;
import org.sopt.dto.post.request.PostUpdateRequest;
import org.sopt.global.dto.ResponseDTO;
import org.sopt.global.exeption.BusinessException;
import org.sopt.global.messeage.business.PostErrorMessage;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<Void>> createPost(@RequestHeader Long userId, @RequestBody final PostRequest postRequest) {
        postService.createPost(userId, postRequest.title(), postRequest.content(),postRequest.tag());
        return ResponseEntity.ok(ResponseDTO.success(null));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<PostResponse>>> getAllPosts() {
        List<PostResponse> results = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.success(results));
    }

    @GetMapping("/{postId}")
    public PostDetailResponse getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @DeleteMapping("/{postId}")
    public void deletePostById(@PathVariable Long postId) {
        postService.deletePostById(postId);
    }

    @PatchMapping("/{postId}")
    public void updatePostTitle(@PathVariable Long postId, @RequestBody final PostUpdateRequest postRequest) {
        postService.updatePostTitle(postId, postRequest.title(), postRequest.content());
    }

    @GetMapping("/search")
    public List<PostDetailResponse> searchPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String username) {

        if (title != null) {
            return postService.searchPostsByTitle(title);
        }
        if (tag != null) {
            try {
                Tag tagEnum  = Tag.valueOf(tag.toUpperCase());
                return postService.searchPostsByTag(tagEnum);
            } catch (IllegalArgumentException e) {
                throw new BusinessException(PostErrorMessage.INVALID_TAG);
            }
        }
        if (username != null) {
            return postService.searchPostsByUserName(username);
        }

        throw new IllegalArgumentException("검색 조건(title, tag, username) 중 하나는 필수입니다.");
    }


}
