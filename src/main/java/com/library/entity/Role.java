package com.library.entity;

import lombok.Getter;

@Getter
public enum Role {
    UNREGISTERED("unregistered"),
    USER("user"),
    LIBRARIAN("librarian"),
    ADMIN("admin");

    private final String roleValue;
    Role(String value) {
        roleValue = value;
    }
}
