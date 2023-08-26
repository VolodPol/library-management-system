package com.library.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Publisher extends Entity {
    private int id;
    private String name;

    public Publisher(String name) {
        this.name = name;
    }

    public Publisher(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
