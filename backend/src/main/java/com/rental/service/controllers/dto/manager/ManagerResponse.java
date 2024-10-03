package com.rental.service.controllers.dto.manager;

import com.rental.service.entities.Manager;
import com.rental.service.enums.Role;

public record ManagerResponse(
        Long id,
        String name,
        String email,
        String register,
        String department,
        String location,
        Role role
) {
    public static ManagerResponse fromManager(Manager manager) {
        return new ManagerResponse(
                manager.getId(),
                manager.getName(),
                manager.getEmail(),
                manager.getRegister(),
                manager.getDepartment(),
                manager.getLocation(),
                manager.getRole()
        );
    }
}
