package com.rental.service.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER");

    private final String name;

    Role(String name) {
        this.name = name;
    }
}
