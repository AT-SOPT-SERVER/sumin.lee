package org.sopt.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.sopt.dto.PostRequest;
import org.sopt.dto.PostResponse;
import org.sopt.dto.PostUpdateRequest;
import org.sopt.global.dto.ResponseDTO;
import org.sopt.service.AwsS3Service;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
public class PostController {

    private final AwsS3Service awsS3Service;
    private final PostService postService;

    public PostController(AwsS3Service awsS3Service,PostService postService) {
        this.awsS3Service = awsS3Service;
        this.postService = postService;
    }


@PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
@Operation(
        summary = "Create a post with photo(s)",
        description = "Create a new post with one or more photos"
)
public ResponseEntity<ResponseDTO<Void>> createPost(@ModelAttribute PostRequest postRequest) {
    List<String> photoUrlList = awsS3Service.uploadFiles(postRequest.photos());
    postService.createPost(postRequest.title(), photoUrlList);
    return ResponseEntity.ok(ResponseDTO.success(null));
}

public ResponseEntity<ResponseDTO<Void>> createPost(
        @RequestPart("data") PostRequest postRequest,
        @RequestPart("photos") List<MultipartFile> photos) {


    List<String> photoUrlList = awsS3Service.uploadFiles(photos);


    postService.createPost(postRequest.title(), photoUrlList);


    return ResponseEntity.ok(ResponseDTO.success(null));
}


    @GetMapping("/post")
    public ResponseEntity<ResponseDTO<List<PostResponse>>>getAllPosts(){

        List<PostResponse> results = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.success(results));
    }


    @GetMapping("/post/{postId}")
    public ResponseEntity<ResponseDTO<PostResponse>> getPostById(@PathVariable Long postId) {
        PostResponse postResponse = postService.getPostById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.success(postResponse));
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
