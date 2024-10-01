package com.rental.service.controllers.dto;

import com.rental.service.entities.User;
import jakarta.validation.constraints.*;

public record UserRequest(
        @NotBlank
        String name,

        @NotBlank @Email
        String email,

        @NotBlank
        @Size(min = 1, max = 8)
        String password
) {
    public User toUser() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
