package com.rental.service.controllers.dto;

import com.rental.service.entities.User;
import com.rental.service.enums.Role;

public record UserResponse(
        Long id,
        String name,
        String email,
        Role role
) {
    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
