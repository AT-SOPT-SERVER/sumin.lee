package org.sopt.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    LocalDateTime createdAt;

    public Post() {

    }

    public Post(String title) {
        this.title = title;
        this.createdAt = LocalDateTime.now();
    }

    public Long getPostId() {
        return id;
    }

    public String getTitle() {
        return this.title;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }
}