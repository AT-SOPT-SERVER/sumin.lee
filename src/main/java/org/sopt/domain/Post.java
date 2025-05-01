package org.sopt.domain;

import jakarta.persistence.*;

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

    public Post() {

    }

    public Post(String title,String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
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

    public User getUser() {
        return user;
    }
}
