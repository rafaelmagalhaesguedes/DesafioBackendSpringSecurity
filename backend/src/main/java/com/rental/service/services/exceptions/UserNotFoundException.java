package com.rental.service.services.exceptions;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User not found.");
    }
}
