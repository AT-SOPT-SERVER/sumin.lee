package org.sopt.repository;

import org.sopt.domain.post.Post;
import org.sopt.domain.post.Tag;
import org.sopt.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByTitle(String title);
    List<Post> findByTitleContaining(String keyword);
    Post findTopByOrderByCreatedAtDesc();
    List<Post> findByTag(Tag tag);
    List<Post> findByUser(User user);
}

