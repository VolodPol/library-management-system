package org.project.entity.sorting;

public enum SortOrder {
    ASC("asc"),
    DESC("desc");
    private final String value;
    SortOrder(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}
