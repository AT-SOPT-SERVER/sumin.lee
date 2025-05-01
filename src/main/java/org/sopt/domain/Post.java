package org.sopt.domain;

import jakarta.persistence.*;
import org.sopt.global.StringListConverter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    LocalDateTime createdAt;


    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> photoUrls;



    protected Post() {

    }


    public Post(String title, List<String> photoUrls) {
        this.title = title;
        this.createdAt = LocalDateTime.now();
        this.photoUrls = photoUrls;
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

    public List<String> getPhotoUrls() {
        return this.photoUrls;
    }
    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }
}