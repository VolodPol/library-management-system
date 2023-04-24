package org.project.entity.impl;

public enum OrderStatus {
    UNCONFIRMED(0),
    CONFIRMED(1);
    private final int statusValue;

    OrderStatus(int value) {
        statusValue = value;
    }
    @SuppressWarnings("unused")
    public int getStatusValue() {
        return statusValue;
    }
}
