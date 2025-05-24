package org.sopt.domain.post;

import jakarta.persistence.*;
import org.sopt.domain.user.User;

import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String content;
    LocalDateTime createdAt;


    @Enumerated(EnumType.STRING)
    private Tag tag;

    public Post() {

    }

    public Post(String title,String content, User user,Tag tag) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.tag = tag;
        this.createdAt = LocalDateTime.now();
    }

    public Long getPostId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return this.content;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }


    public void updateContent(String newTitle, String content) {
        this.title = newTitle;
        this.content = content;
    }
    public Tag getTag(){
        return tag;
    }
    public User getUser() {
        return user;
    }
}
