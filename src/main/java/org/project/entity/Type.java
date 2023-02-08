package org.project.entity;

public enum Type {
    SUBSCRIPTION("subscription"),
    READING_ROOM("reading_room");
    private final String value;
    Type(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }

}
