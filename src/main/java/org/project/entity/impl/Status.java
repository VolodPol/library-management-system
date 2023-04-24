package org.project.entity.impl;
@SuppressWarnings("unused")
public enum Status {
    PENDING(1),
    CONFIRMED(2);

    private final int statusValue;

    Status(int value) {
        statusValue = value;
    }

    public int getStatusValue() {
        return statusValue;
    }
}
