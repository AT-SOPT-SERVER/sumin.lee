package org.sopt.service;

import org.sopt.domain.post.Post;
import org.sopt.domain.post.Tag;
import org.sopt.domain.user.User;
import org.sopt.dto.post.response.PostDetailResponse;
import org.sopt.global.exeption.BusinessException;
import org.sopt.global.messeage.business.PostErrorMessage;
import org.sopt.global.messeage.business.UserErrorMessage;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sopt.dto.post.response.PostResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    public PostService(PostRepository postRepository,UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    private static final long MINIMUM_TIME_BETWEEN_POSTS = 3;


    public void createPost(Long userId, String title,String content,Tag tag) {


        if (postRepository.existsByTitle(title)) {
            throw new BusinessException(PostErrorMessage.POST_TITLE_DUPLICATE);
        }
        if (title == null || title.length() > 30) {
            throw new BusinessException(PostErrorMessage.INVALID_TITLE_LENGTH);
        }

        if (content.length() > 1000) {
            throw new BusinessException(PostErrorMessage.INVALID_CONTENT_LENGTH);
        }

        Post lastPost = postRepository.findTopByOrderByCreatedAtDesc();
        checkLastPostTime(lastPost);

        User user = userRepository.findById(userId).orElseThrow(()-> new BusinessException(UserErrorMessage.USER_NOT_FOUND));
        Post post = new Post(title,content,user,tag);
        postRepository.save(post);
    }

    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> new PostResponse(post.getTitle(),post.getUser().getUserName()))
                .toList();
    }


    public PostDetailResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new BusinessException(PostErrorMessage.POST_NOT_FOUND));
        return new PostDetailResponse(post.getTitle(),post.getContent(),post.getTag(), post.getUser().getUserName());

    }

    public void deletePostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new BusinessException(PostErrorMessage.POST_NOT_FOUND));
        postRepository.deleteById(postId);
    }

    @Transactional
    public void updatePostTitle(Long updatePostId, String newTitle , String newContent) {

        if (newTitle == null || newTitle.length() > 30) {
            throw new BusinessException(PostErrorMessage.INVALID_TITLE_LENGTH);
        }

        if (postRepository.existsByTitle(newTitle)) {
            throw new BusinessException(PostErrorMessage.POST_TITLE_DUPLICATE);
        }

        Post post = postRepository.findById(updatePostId).orElseThrow(() -> new BusinessException(PostErrorMessage.POST_NOT_FOUND));

        post.updateContent(newTitle,newContent);

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

    public List<PostDetailResponse> searchPostsByTitle(String title){
        List<Post> posts = postRepository.findByTitleContaining(title);
        if (posts.isEmpty()) {
            throw new BusinessException(PostErrorMessage.POST_NOT_FOUND);
        }
        return posts.stream()
                .map(post -> new PostDetailResponse(post.getTitle(),post.getContent(),post.getTag(),post.getUser().getUserName()))
                .toList();
    }

    public List<PostDetailResponse> searchPostsByTag(Tag tag){
        List<Post> posts = postRepository.findByTag(tag);
        if (posts.isEmpty()){
            throw new BusinessException(PostErrorMessage.POST_NOT_FOUND);
        }

        return posts.stream()
                .map(post -> new PostDetailResponse(post.getTitle(),post.getContent(),post.getTag(),post.getUser().getUserName())).toList();
    }
    public List<PostDetailResponse> searchPostsByUserName(String keyword) {

        List<User> users = userRepository.findByUserNameContaining(keyword);
        if (users.isEmpty()) {
            throw new BusinessException(UserErrorMessage.USER_NOT_FOUND);
        }



        List<Post> posts = new ArrayList<>();
        for (User user : users) {
            List<Post> userPosts = postRepository.findByUser(user);
            posts.addAll(userPosts);
        }
        if (posts.isEmpty()) {
            throw new BusinessException(PostErrorMessage.POST_NOT_FOUND);
        }

        return posts.stream()
                .map(post -> new PostDetailResponse(post.getTitle(), post.getContent(), post.getTag(), post.getUser().getUserName()))
                .toList();
    }
}
