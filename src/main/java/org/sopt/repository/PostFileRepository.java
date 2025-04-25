package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.repository.storage.FileStorage;
import org.sopt.repository.storage.PostFileStorage;

import java.util.*;

public class PostFileRepository implements PostRepository {

    private final Map<Integer, Post> postMap = new HashMap<>();
    private final FileStorage<Post> storage = new PostFileStorage();

    public PostFileRepository() {
        List<Post> loadedPosts = storage.load(); // 파일에서 불러온 게시글들
        for (Post post : loadedPosts) {
            postMap.put(post.getId(), post); // 불러온 데이터를 Map에 저장
        }
    }

    @Override
    public void save(Post post) {
        postMap.put(post.getId(), post); // Map에 저장
        storage.save(new ArrayList<>(postMap.values())); // 파일에도 저장 (Map의 값들을 리스트로 변환)
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(postMap.values());
    }
    @Override
    public Optional<Post> findOneById(int id) {
        return Optional.ofNullable(postMap.get(id)); // ID로 찾기
    }
    @Override
    public boolean deletePostById(int id) {
        boolean deleted = postMap.remove(id) != null; // ID로 삭제
        if (deleted) {
            storage.save(new ArrayList<>(postMap.values())); // 삭제 후 파일 갱신
        }
        return deleted;
    }

    @Override
    public boolean isExistByTitle(String title) {
        return postMap.values().stream()
                .anyMatch(post -> post.getTitle().equals(title)); // 타이틀로 검색
    }

    @Override
    public List<Post> findByTitle(String title) {
        return postMap.values().stream()
                .filter(post -> post.getTitle().contains(title)) // 타이틀이 포함된 게시글 찾기
                .toList();
    }
}

