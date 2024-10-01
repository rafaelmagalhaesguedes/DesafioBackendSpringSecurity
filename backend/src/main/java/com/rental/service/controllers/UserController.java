package com.rental.service.controllers;

import com.rental.service.controllers.dto.UserRequest;
import com.rental.service.controllers.dto.UserResponse;
import com.rental.service.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse saveUser(@RequestBody @Valid UserRequest userRequest) {
        var user = userService.save(userRequest.toUser());
        return UserResponse.fromUser(user);
    }
}
