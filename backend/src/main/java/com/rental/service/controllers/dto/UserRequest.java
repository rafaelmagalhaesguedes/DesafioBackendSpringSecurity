package com.rental.service.controllers.dto;

import com.rental.service.entities.User;
import com.rental.service.enums.Role;
import com.rental.service.services.validations.ValidRole;
import jakarta.validation.constraints.*;

public record UserRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 1, max = 8, message = "Password must be between 1 and 8 characters")
        String password,

        @NotBlank(message = "Role cannot be blank")
        @ValidRole
        String role
) {
    public User toUser() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(Role.valueOf(role.toUpperCase()))
                .build();
    }
}