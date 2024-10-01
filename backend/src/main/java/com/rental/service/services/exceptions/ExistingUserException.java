package com.rental.service.services.exceptions;

public class ExistingUserException extends NotFoundException {
    public ExistingUserException() {
        super("User already exists.");
    }
}
