package org.project.entity.sorting;

public enum Sorting {
    ASC("asc"),
    DESC("desc");
    private final String value;
    Sorting(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}
