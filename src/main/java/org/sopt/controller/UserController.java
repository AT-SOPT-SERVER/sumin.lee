package org.sopt.controller;


import org.sopt.dto.user.UserCreateRequestDTO;
import org.sopt.global.dto.ResponseDTO;
import org.sopt.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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

}
