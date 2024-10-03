package com.rental.service.controllers.dto.admin;

import com.rental.service.entities.Admin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdminUpdateRequest(
        @Size(min = 2, max = 100, message = "Name must be between 2 and 50 characters")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "Name must contain only alphabetic characters and spaces")
        String name,

        @Email(message = "Email should be valid")
        String email
) {
    public Admin toAdmin() {
        return Admin.builder()
                .name(name)
                .email(email)
                .build();
    }
}
