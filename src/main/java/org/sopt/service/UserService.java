package org.sopt.service;


import org.sopt.domain.user.User;
import org.sopt.dto.user.UserResponse;
import org.sopt.global.exeption.BusinessException;
import org.sopt.global.messeage.business.UserErrorMessage;
import org.sopt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public void createUser(String userName) {
        if (userName == null || userName.length() > 10) {
            throw new BusinessException(UserErrorMessage.INVALID_USERNAME_LENGTH);
        }

        User user = new User(userName);
        userRepository.save(user);
    }


    public List<UserResponse> searchUsersByName(String userName){
        List<User> users = userRepository.findByUserNameContaining(userName);
        if (users.isEmpty()) {
            throw new BusinessException(UserErrorMessage.USER_NOT_FOUND);
        }

        return users.stream()
                .map(user -> new UserResponse(user.getUserId(),user.getUserName()))
                .toList();
    }

}
