package org.project.entity;

public enum Subscription {
    BASIC(0),
    READER(1);


    private final int value;

    Subscription(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
