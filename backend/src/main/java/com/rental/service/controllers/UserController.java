package com.rental.service.controllers;

import com.rental.service.controllers.dto.UserRequest;
import com.rental.service.controllers.dto.UserResponse;
import com.rental.service.entities.User;
import com.rental.service.services.UserService;
import com.rental.service.services.exceptions.ExistingUserException;
import com.rental.service.services.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public UserResponse saveUser(@RequestBody @Valid UserRequest userRequest) throws ExistingUserException {
        var user = userService.save(userRequest.toUser());
        return UserResponse.fromUser(user);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long userId) throws UserNotFoundException {
        var user = userService.findById(userId);
        return UserResponse.fromUser(user);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long userId, @RequestBody @Valid UserRequest userRequest) throws UserNotFoundException {
        var updatedUser = userService.update(userId, userRequest.toUser());
        return UserResponse.fromUser(updatedUser);
    }

    @DeleteMapping("/{id}")
    public UserResponse deleteUser(@PathVariable Long userId) throws UserNotFoundException {
        var deletedUser = userService.delete(userId);
        return UserResponse.fromUser(deletedUser);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponse> findAllUsers() {
        List<User> userList = userService.list();

        return  userList.stream()
                .map(UserResponse::fromUser)
                .collect(Collectors.toList());
    }
}
