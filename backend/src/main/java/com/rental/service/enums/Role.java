package com.rental.service.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    CUSTOMER("CUSTOMER");

    private final String name;

    Role(String name) {
        this.name = name;
    }
}
