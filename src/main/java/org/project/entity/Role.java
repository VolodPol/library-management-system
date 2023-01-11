package org.project.entity;

public enum Role {
    UNREGISTERED(0),
    USER(1),
    LIBRARIAN(2),
    ADMIN(3);

    private final int roleValue;
    Role(int value) {
        roleValue = value;
    }

    public int getRoleValue() {
        return roleValue;
    }
}
