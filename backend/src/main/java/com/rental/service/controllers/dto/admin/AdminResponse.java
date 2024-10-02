package com.rental.service.controllers.dto.admin;

import com.rental.service.entities.Admin;
import com.rental.service.enums.Role;

public record AdminResponse(
        String name,
        String email,
        Role role
) {
    public static AdminResponse fromAdmin(Admin admin) {
        return new AdminResponse(
                admin.getName(),
                admin.getEmail(),
                admin.getRole()
        );
    }
}
