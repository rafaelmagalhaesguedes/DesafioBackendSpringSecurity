package com.rental.service.services.exceptions;

public class AdminNotFoundException extends NotFoundException {
    public AdminNotFoundException() {
        super("Admin not found.");
    }
}
