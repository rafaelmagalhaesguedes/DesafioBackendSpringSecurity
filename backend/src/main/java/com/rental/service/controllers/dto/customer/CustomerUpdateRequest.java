package com.rental.service.controllers.dto.customer;

import com.rental.service.entities.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerUpdateRequest(
        @Size(min = 2, max = 100, message = "Name must be between 2 and 50 characters")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "Name must contain only alphabetic characters and spaces")
        String name,

        @Email(message = "Email should be valid")
        String email,

        @CPF
        String document,

        @Pattern(regexp = "^\\+55\\s?\\(?\\d{2}\\)?\\s?\\d{5}-\\d{4}$|^\\(?\\d{2}\\)?\\s?\\d{5}-\\d{4}$|^\\d{5}-\\d{4}$",
                message = "Number phone should be valid")
        String numberPhone
) {
    public Customer toCustomer() {
        return Customer.builder()
                .name(name)
                .email(email)
                .document(document)
                .numberPhone(numberPhone)
                .build();
    }
}