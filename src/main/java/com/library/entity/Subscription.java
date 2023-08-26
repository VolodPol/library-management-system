package com.library.entity;

import lombok.Getter;

@Getter
public enum Subscription {
    BASIC("basic"),
    READER("reader");

    private final String value;
    Subscription(String value) {
        this.value = value;
    }
}

