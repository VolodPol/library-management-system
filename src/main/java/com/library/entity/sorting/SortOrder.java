package com.library.entity.sorting;

import lombok.Getter;

@Getter
public enum SortOrder {
    ASC("asc"),
    DESC("desc");
    private final String value;
    SortOrder(String value) {
        this.value = value;
    }
}
