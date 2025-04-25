package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.exception.DuplicatePostTitleException;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostFileRepository;
import org.sopt.repository.PostRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.sopt.util.PostIdUtil.generateNewId;

public class PostService {

    private PostRepository postRepository;

    private static final long MINIMUM_TIME_BETWEEN_POSTS = 3;


    public PostService() {
        this.postRepository = new PostFileRepository();
    }
    public void createPost(String title) {
        if (postRepository.isExistByTitle(title)) {
            throw new DuplicatePostTitleException();
        }

        List<Post> allPosts = postRepository.findAll();
        checkLastPostTime(allPosts);

        Post post = new Post(generateNewId(allPosts), title);
        postRepository.save(post);
    }

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }


    public Post getPostById(int id) {
        return postRepository.findOneById(id).orElseThrow(()-> new PostNotFoundException());
    }

    public boolean deletePostById(int id) {
        return postRepository.deletePostById(id);
    }

    public boolean updatePostTitle(int updatePostId, String newTitle) {

        if (postRepository.isExistByTitle(newTitle)) {
            throw new DuplicatePostTitleException();
        }

        Post post = postRepository.findOneById(updatePostId).orElseThrow(() -> new PostNotFoundException());

        post.updateTitle(newTitle);

        postRepository.save(post);
        return true;

    }

    public void checkLastPostTime(List<Post> posts){
        if (posts.isEmpty()) return;

        Post lastPost = posts.get(posts.size()-1);
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

    public List<Post> searchPostsByTitle(String title){
        return postRepository.findByTitle(title);
    }


}
