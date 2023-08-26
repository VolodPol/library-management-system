package com.library.entity;

import lombok.Getter;

@Getter
public enum Status {
    PENDING(1),
    CONFIRMED(2);

    private final int statusValue;

    Status(int value) {
        statusValue = value;
    }
}
