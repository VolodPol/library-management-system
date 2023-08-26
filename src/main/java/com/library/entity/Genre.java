package com.library.entity;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Genre extends Entity {
    private int id;
    private String name;
}
