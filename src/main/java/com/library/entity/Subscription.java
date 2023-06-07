package com.library.entity;

public enum Subscription {
    BASIC("basic"),
    READER("reader");

    private final String value;
    Subscription(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

