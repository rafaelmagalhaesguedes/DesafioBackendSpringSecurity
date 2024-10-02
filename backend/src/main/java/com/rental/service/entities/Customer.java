package com.rental.service.entities;

import com.rental.service.enums.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("CUSTOMER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {
    private String document;
    private String numberPhone;

    @Builder
    public Customer(String name, String email, String password, Role role, String document, String numberPhone) {
        super(name, email, password, role);
        this.document = document;
        this.numberPhone = numberPhone;
    }
}