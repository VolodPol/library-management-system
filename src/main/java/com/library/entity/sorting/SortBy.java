package com.library.entity.sorting;

import lombok.Getter;

@Getter
public enum SortBy {
    DEFAULT(""),
    TITLE("title"),
    AUTHOR("author"),
    PUBLICATION("name"),
    DATE("date_of_publication");
    private final String value;
    SortBy(String value) {
        this.value = value;
    }
}
