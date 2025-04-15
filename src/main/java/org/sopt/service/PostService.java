package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;

import java.util.List;

public class PostService { //from 레포지토리 to 컨트롤러

    //PostRepository(게시물 저장소->삭제,수정,저장 등 함수 구현되어있음) 가져오기
    //PostService(저장소를 사용)


    private PostRepository postRepository = new PostRepository();


    public void createPost(Post post){ //Post->controller로부터 받아옴
        postRepository.save(post);
    }

    public List<Post> getAllPost(){
        return postRepository.findAll();
    }


    public Post getPostById(int id) {
        return postRepository.findOneById(id);
    }

    public boolean deletePostById(int id) {
        return postRepository.deletePostById(id);
    }

    public boolean updatePostById(int id, String newTitle){
        return postRepository.updatePostById(id, newTitle);}

}
