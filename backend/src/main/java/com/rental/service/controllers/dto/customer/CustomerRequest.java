package com.rental.service.controllers.dto.customer;

import com.rental.service.entities.Customer;
import com.rental.service.enums.Role;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerRequest(
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

        @NotBlank(message = "Document cannot be blank")
        @CPF
        String document,

        @NotBlank(message = "Number phone cannot be blank")
        @Pattern(regexp = "^\\+55\\s?\\(?\\d{2}\\)?\\s?\\d{5}-\\d{4}$|^\\(?\\d{2}\\)?\\s?\\d{5}-\\d{4}$|^\\d{5}-\\d{4}$",
                message = "Number phone should be valid")
        String numberPhone
) {
    public Customer toCustomer() {
        return Customer.builder()
                .name(name)
                .email(email)
                .password(password)
                .document(document)
                .numberPhone(numberPhone)
                .role(Role.CUSTOMER)
                .build();
    }
}