package org.sopt.domain;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @OneToMany(mappedBy="user")
    private List<Post> posts= new ArrayList<>();


    public User() {
    }
    public User(String userName) {
        this.userName = userName;

    }

    public Long getUserId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }
}
