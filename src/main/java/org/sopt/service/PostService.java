package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.exception.DuplicatePostTitleException;
import org.sopt.exception.PostNotFoundException;
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
            throw new DuplicatePostTitleException();
        }

        Post lastPost = postRepository.findTopByOrderByCreatedAtDesc();
        checkLastPostTime(lastPost);

        Post post = new Post(title);
        postRepository.save(post);
    }

    public List<PostResponse> getAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> new PostResponse(post.getTitle()))
                .collect(Collectors.toList());
    }


    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException());
        return new PostResponse(post.getTitle());

    }

    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException());
        postRepository.deleteById(id);
    }

    @Transactional
    public void updatePostTitle(Long updatePostId, String newTitle) {

        if (newTitle == null || newTitle.length() > 30) {
            throw new IllegalArgumentException("제목은 1자 이상 30자 이하여야함");
        }

        if (postRepository.existsByTitle(newTitle)) {
            throw new DuplicatePostTitleException();
        }

        Post post = postRepository.findById(updatePostId).orElseThrow(() -> new PostNotFoundException());

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

            long minutes = remainingTimeInSeconds / 60;
            long seconds = remainingTimeInSeconds % 60;

            throw new RuntimeException(minutes + "분 " + seconds + "초 후에 다시 시도해주세요.");
        }
    }

    public List<PostResponse> searchPostsByTitle(String title){
        List<Post> posts = postRepository.findByTitleContaining(title);
        if (posts.isEmpty()) {
            throw new PostNotFoundException();
        }

        return posts.stream()
                .map(post -> new PostResponse(post.getTitle()))
                .collect(Collectors.toList());
    }


}
