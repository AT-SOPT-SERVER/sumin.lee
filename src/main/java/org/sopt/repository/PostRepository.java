package org.sopt.repository;

import org.sopt.domain.post.Post;
import org.sopt.domain.post.Tag;
import org.sopt.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByTitle(String title);


    @Query(value = "SELECT * FROM post WHERE MATCH(title) AGAINST(:keyword IN NATURAL LANGUAGE MODE)", nativeQuery = true)
    List<Post> findByTitleContaining(String keyword);

    Post findTopByOrderByCreatedAtDesc();

    @Query(value = "SELECT * FROM post WHERE tag_id = :tagId", nativeQuery = true)
    List<Post> findByTag(Tag tag);

    @Query(value = "SELECT * FROM post WHERE user_id = :userId", nativeQuery = true)
    List<Post> findByUser(User user);



}

