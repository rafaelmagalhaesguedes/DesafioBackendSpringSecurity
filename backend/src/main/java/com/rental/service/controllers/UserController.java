package com.rental.service.controllers;

import com.rental.service.controllers.dto.UserRequest;
import com.rental.service.controllers.dto.UserResponse;
import com.rental.service.services.UserService;
import com.rental.service.services.exceptions.ExistingUserException;
import com.rental.service.services.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse saveUser(@RequestBody @Valid UserRequest request) throws ExistingUserException {
        var user = userService.save(request.toUser());
        return UserResponse.fromUser(user);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) throws UserNotFoundException {
        var user = userService.findById(id);
        return UserResponse.fromUser(user);
    }
}
