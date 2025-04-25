package org.sopt.repository;

import org.sopt.domain.Post;

import java.util.*;

public class InMemoryPostRepository implements PostRepository{

    private final Map<Integer, Post> postMap = new HashMap<>();



    @Override
    public void save(Post post){
        postMap.put(post.getId(), post);
    }

    @Override
    public List<Post> findAll(){
        return new ArrayList<>(postMap.values());
    }

    @Override
    public Optional<Post> findOneById(int id){
        return Optional.ofNullable(postMap.get(id));
    }

    @Override
    public boolean deletePostById(int id){
        return postMap.remove(id) !=null;
    }



    @Override
    public boolean isExistByTitle(String title){
        return postMap.values().stream().anyMatch(post ->  post.getTitle().equals(title));
    }

    @Override
    public List<Post> findByTitle(String title) {
        return postMap.values().stream().filter(post -> post.getTitle().contains(title)).toList();
    }
}
