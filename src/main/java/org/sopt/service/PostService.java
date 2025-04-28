package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.exception.DuplicatePostTitleException;
import org.sopt.exception.PostNotFoundException;
import org.sopt.global.exeption.BusinessException;
import org.sopt.global.messeage.business.PostErrorMessage;
import org.sopt.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sopt.dto.PostResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.time.Duration;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    private static final long MINIMUM_TIME_BETWEEN_POSTS = 3;


    public void createPost(String title) {
        if (postRepository.existsByTitle(title)) {
            throw new BusinessException(PostErrorMessage.POST_TITLE_DUPLICATE);
        }

        Post lastPost = postRepository.findTopByOrderByCreatedAtDesc();
        checkLastPostTime(lastPost);

        Post post = new Post(title);
        postRepository.save(post);
    }

    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> new PostResponse(post.getPostId(),post.getTitle(),post.getCreatedAt()))
                .collect(Collectors.toList());
    }


    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new BusinessException(PostErrorMessage.POST_NOT_FOUND));
        return new PostResponse(post.getPostId(), post.getTitle(), post.getCreatedAt());

    }

    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new BusinessException(PostErrorMessage.POST_NOT_FOUND));
        postRepository.deleteById(id);
    }

    @Transactional
    public void updatePostTitle(Long updatePostId, String newTitle) {


        System.out.println("New Title: " + newTitle);
        if (newTitle == null || newTitle.length() > 30) {
            throw new BusinessException(PostErrorMessage.INVALID_TITLE_LENGTH);
        }

        if (postRepository.existsByTitle(newTitle)) {
            throw new BusinessException(PostErrorMessage.POST_TITLE_DUPLICATE);
        }

        Post post = postRepository.findById(updatePostId).orElseThrow(() -> new BusinessException(PostErrorMessage.POST_NOT_FOUND));

        post.updateTitle(newTitle);

    }

    public void checkLastPostTime(Post lastPost){

        if (lastPost == null) {
            return; // 첫 번째 게시글이므로 시간 제한을 적용할 필요 없음
        }
        LocalDateTime lastTime = lastPost.getCreatedAt();
        LocalDateTime now = LocalDateTime.now();

        long elapsedTime = Duration.between(lastTime,now).toSeconds();
        if ( elapsedTime< MINIMUM_TIME_BETWEEN_POSTS){
            long remainingTimeInSeconds = MINIMUM_TIME_BETWEEN_POSTS - elapsedTime;
            throw new BusinessException(PostErrorMessage.POST_CREATION_TIME_LIMIT_EXCEEDED);
        }
    }

    public List<PostResponse> searchPostsByTitle(String title){
        List<Post> posts = postRepository.findByTitleContaining(title);
        if (posts.isEmpty()) {
            throw new BusinessException(PostErrorMessage.POST_NOT_FOUND);
        }

        return posts.stream()
                .map(post -> new PostResponse(post.getPostId(),post.getTitle(),post.getCreatedAt()))
                .collect(Collectors.toList());
    }


}
