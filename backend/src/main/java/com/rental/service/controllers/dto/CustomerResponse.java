package com.rental.service.controllers.dto;

import com.rental.service.entities.Customer;
import com.rental.service.enums.Role;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        String document,
        String numberPhone,
        Role role
) {
    public static CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getDocument(),
                customer.getNumberPhone(),
                customer.getRole()
        );
    }
}
