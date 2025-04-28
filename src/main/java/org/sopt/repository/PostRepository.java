package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.dto.PostResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<PostResponse> findByTitle(String title);
    boolean existsByTitle(String title);
    List<Post> findByTitleContaining(String keyword);
    Post findTopByOrderByCreatedAtDesc();
}


//package org.sopt.repository;
//
//
//import org.sopt.domain.Post;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public interface PostRepository {
//    List<Post> postList = new ArrayList<>();
//    public void save(Post post);
//    //전체 객체에 대한 조회
//    public List<Post> findAll();
//    public Optional<Post> findOneById(int id);
//    public boolean deletePostById(int id);
//    public boolean isExistByTitle(String title);
//    List<Post> findByTitle(String title);
//}
