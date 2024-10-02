package com.rental.service.entities;

import com.rental.service.enums.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("ADMIN")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User {
    private String department;
    private String register;

    @Builder
    public Admin(String name, String email, String password, Role role, String department, String register) {
        super(name, email, password, role);
        this.department = department;
        this.register = register;
    }
}