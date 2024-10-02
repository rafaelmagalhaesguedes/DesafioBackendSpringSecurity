package com.rental.service.entities;

import static com.rental.service.services.CryptoService.*;

import com.rental.service.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("CUSTOMER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {

    @Column(name = "document")
    private String encryptedDocument;

    @Column(name = "numberPhone")
    private String encryptedNumberPhone;

    @Transient
    private String rawDocument;

    @Transient
    private String rawNumberPhone;

    @Builder
    public Customer(String name, String email, String password, Role role, String document, String numberPhone) {
        super(name, email, password, role);
        this.rawDocument = document;
        this.rawNumberPhone = numberPhone;
    }

    @PrePersist
    @PreUpdate
    public void encryptFields() {
        this.encryptedDocument = encrypt(rawDocument);
        this.encryptedNumberPhone = encrypt(rawNumberPhone);
    }

    @PostLoad
    public void decryptFields() {
        this.rawDocument = decrypt(encryptedDocument);
        this.rawNumberPhone = decrypt(encryptedNumberPhone);
    }
}