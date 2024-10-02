package com.rental.service.services.exceptions;

public class ExistingUserException extends RuntimeException {
    public ExistingUserException() {
        super("User already exists.");
    }
}
