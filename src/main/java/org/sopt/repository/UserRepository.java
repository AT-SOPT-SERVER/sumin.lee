package org.sopt.repository;

import org.sopt.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByUserNameContaining(String keyword);
}
