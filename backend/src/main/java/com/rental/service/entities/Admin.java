package com.rental.service.entities;

import com.rental.service.enums.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("ADMIN")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {
    private boolean isActive;
    private boolean isDeleted;

    @Builder
    public Admin(String name, String email, String password, Role role) {
        super(name, email, password, role);
        this.isActive = true;
        this.isDeleted = false;
    }
}