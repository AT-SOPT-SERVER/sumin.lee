package org.sopt.service;


import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.global.exeption.BusinessException;
import org.sopt.global.messeage.business.PostErrorMessage;
import org.sopt.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public void createUser(String userName) {
        User user = new User(userName);
        userRepository.save(user);
    }
}
