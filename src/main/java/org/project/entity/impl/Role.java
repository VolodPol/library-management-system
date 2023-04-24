package org.project.entity.impl;

public enum Role {
    UNREGISTERED("unregistered"),
    USER("user"),
    LIBRARIAN("librarian"),
    ADMIN("admin");

    private final String roleValue;
    Role(String value) {
        roleValue = value;
    }

    public String getRoleValue() {
        return roleValue;
    }
}
