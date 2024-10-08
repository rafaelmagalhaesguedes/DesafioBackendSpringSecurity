package com.rental.service.entities;

import com.rental.service.enums.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("MANAGER")
@Getter
@Setter
@NoArgsConstructor
public class Manager extends Admin {
    private String register;
    private String department;
    private String location;

    @Builder(builderMethodName = "managerBuilder")
    public Manager(String name, String email, String password, Role role, String register, String department, String location) {
        super(name, email, password, role);
        this.register = register;
        this.department = department;
        this.location = location;
    }
}