package org.sopt.controller;


import org.sopt.dto.user.UserCreateRequest;
import org.sopt.dto.user.UserResponse;
import org.sopt.global.dto.ResponseDTO;
import org.sopt.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/user")
    public ResponseEntity<ResponseDTO<Void>> createUser(@RequestBody UserCreateRequest requestDTO){
        userService.createUser(requestDTO.userName());
        return ResponseEntity.ok(ResponseDTO.success(null));
    }

    @GetMapping("/user/search")
    public List<UserResponse> searchUsersByKeyword(@RequestParam String keyword) {
        return userService.searchUsersByName(keyword);
    }

}
