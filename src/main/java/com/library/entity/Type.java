package com.library.entity;

import lombok.Getter;

@Getter
public enum Type {
    SUBSCRIPTION("subscription"),
    READING_ROOM("reading_room");
    private final String value;
    Type(String value) {
        this.value = value;
    }
}
