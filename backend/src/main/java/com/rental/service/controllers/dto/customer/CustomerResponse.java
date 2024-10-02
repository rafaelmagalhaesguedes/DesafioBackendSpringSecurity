package com.rental.service.controllers.dto.customer;

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
                customer.getRawDocument(),
                customer.getRawNumberPhone(),
                customer.getRole()
        );
    }
}
