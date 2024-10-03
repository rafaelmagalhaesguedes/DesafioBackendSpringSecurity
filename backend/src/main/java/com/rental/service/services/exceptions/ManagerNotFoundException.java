package com.rental.service.services.exceptions;

public class ManagerNotFoundException extends NotFoundException {
    public ManagerNotFoundException() {
        super("Manager not found.");
    }
}
