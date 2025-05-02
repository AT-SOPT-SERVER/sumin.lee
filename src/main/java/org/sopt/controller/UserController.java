package org.sopt.controller;


import org.sopt.dto.post.response.PostDetailResponse;
import org.sopt.dto.user.UserCreateRequestDTO;
import org.sopt.dto.user.UserResponseDTO;
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
    public ResponseEntity<ResponseDTO<Void>> createUser(@RequestBody UserCreateRequestDTO requestDTO){
        userService.createUser(requestDTO.userName());
        return ResponseEntity.ok(ResponseDTO.success(null));
    }

    @GetMapping("/user/search")
    public List<UserResponseDTO> searchUsersByKeyword(@RequestParam String keyword) {
        return userService.searchUsersByName(keyword);
    }

}
