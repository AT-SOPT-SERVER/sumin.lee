package org.sopt.domain;

import java.time.LocalDateTime;

public class Post {

    private int id;
    private String title;
    private final LocalDateTime createdAt;

    //생성자
    public Post(int id,String title){
        this.id = id;
        this.title = title;
        this.createdAt = LocalDateTime.now();
    }

    public Post(int id, String title, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
    }
    public int getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }



    public void updateTitle(String newTitle){
        this.title = newTitle;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
}
