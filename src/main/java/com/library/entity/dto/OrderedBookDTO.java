package com.library.entity.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@EqualsAndHashCode
@Getter
@Setter
public class OrderedBookDTO {
    private int bookId;
    private String title;
    private String author;
    private Timestamp orderDate;
    private Timestamp returnDate;
    private String finedStatus;
    private String subscription;
}
