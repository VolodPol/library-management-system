package com.library.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
    UNCONFIRMED(0),
    CONFIRMED(1);
    private final int statusValue;

    OrderStatus(int value) {
        statusValue = value;
    }
}
