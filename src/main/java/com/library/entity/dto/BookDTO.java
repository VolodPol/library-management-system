package com.library.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class BookDTO {
    private String title;
    private String author;
    private String isbn;
    private int copiesNumber;
    private Date dateOfPublication;
    private String publisher;
    public BookDTO() {}
}
