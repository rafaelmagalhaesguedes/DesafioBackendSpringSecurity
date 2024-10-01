package com.rental.service.controllers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @NotBlank @Email
        String email,

        @NotBlank
        @Size(min = 1, max = 8)
        String password
) { }
