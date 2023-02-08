package org.project.entity.sorting;

public enum OrderType {
    DEFAULT(""),
    TITLE("title"),
    AUTHOR("author"),
    PUBLICATION("name"),
    DATE("date_of_publication");
    private final String value;
    OrderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
