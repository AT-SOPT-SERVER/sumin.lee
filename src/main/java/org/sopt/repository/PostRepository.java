package org.sopt.repository;


import org.sopt.domain.Post;

import java.util.ArrayList;
import java.util.List;

public class PostRepository {
    List<Post> postList = new ArrayList<>();

    public void save(Post post){
        postList.add(post);
    }

    //전체 객체에 대한 조회
    public List<Post> findAll(){
        return postList;
    }


    public Post findOneById(int id) {
        Post result = null;
        for (Post post : postList) {
            if (post.getId() == id) {
                return post;
            }
        }
        return null;
    }

    public boolean deletePostById(int id) {
        for (Post post : postList) {
            if (post.getId() == id) {
                postList.remove(post);
                return true;
            }
        }
        return false;
    }


    //게시물 수정 추가
    public boolean updatePostById(int id, String newTitle){
        for(int i = 0; i<postList.size(); i++){
            Post post = postList.get(i);
            if (post.getId() == id){
                Post updatedPost = new Post(id,newTitle);
                postList.set(i,updatedPost);
                return true;
            }
        }
        return false;
    }
}
