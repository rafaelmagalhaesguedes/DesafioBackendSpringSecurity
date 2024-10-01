package com.rental.service.controllers.dto;

import com.rental.service.entities.User;
import com.rental.service.enums.Role;
import com.rental.service.services.validations.ValidRole;
import jakarta.validation.constraints.*;

public record UserRequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 50 characters")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "Name must contain only alphabetic characters and spaces")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
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