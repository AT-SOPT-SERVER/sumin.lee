package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.dto.post.response.PostResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    //Optional<PostResponse> findByTitle(String title);
    List<Post> findByUser_UserName(String userName);
    boolean existsByTitle(String title);
    List<Post> findByTitleContaining(String keyword);
    Post findTopByOrderByCreatedAtDesc();
}

