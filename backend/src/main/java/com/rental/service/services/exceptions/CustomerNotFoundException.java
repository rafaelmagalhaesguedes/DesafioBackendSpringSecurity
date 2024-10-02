package com.rental.service.services.exceptions;

public class CustomerNotFoundException extends NotFoundException {
    public CustomerNotFoundException() {
        super("Customer not found.");
    }
}
