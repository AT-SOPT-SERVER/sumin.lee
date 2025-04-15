package org.sopt.domain;

public class Post {

    public Object remove;
    private int id; //private -> PostController,PostService,PostRepository에서 이 값들에 접근 및 수정하려면 -> getter,setter필요
    private String title;

    //생성자
    public Post(int id,String title){
        this.id = id;
        this.title = title;
    }

    public int getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

}
