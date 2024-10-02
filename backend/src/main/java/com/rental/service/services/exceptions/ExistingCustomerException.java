package com.rental.service.services.exceptions;

public class ExistingCustomerException extends NotFoundException {
    public ExistingCustomerException() {
        super("Customer already exists.");
    }
}
