package com.library.entity;


import com.library.utils.UtilProvider;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Book extends Entity {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private int copiesNumber;
    private Date dateOfPublication;
    private Publisher publisher;

    public Book() {}

    private Book(BookBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.author = builder.author;
        this.isbn = builder.isbn;
        this.copiesNumber = builder.copiesNumber;
        this.dateOfPublication = builder.dateOfPublication;
        this.publisher = builder.publisher;
    }

    public static final class BookBuilder {
        private int id;
        private String title;
        private String author;
        private String isbn;
        private int copiesNumber;
        private Date dateOfPublication;
        private Publisher publisher;

        public BookBuilder addId(int id) {
            this.id = id;
            return this;
        }
        public BookBuilder addTitle(String title) {
            this.title = title;
            return this;
        }
        public BookBuilder addAuthor(String author) {
            this.author = author;
            return this;
        }
        public BookBuilder addIsbn(String isbn) {
            this.isbn = isbn;
            return this;
        }
        public BookBuilder addCopiesNumber(int copiesNumber) {
            this.copiesNumber = copiesNumber;
            return this;
        }
        public BookBuilder addDateOfPublication(Date dateOfPublication) {
            this.dateOfPublication = dateOfPublication;
            return this;
        }
        public BookBuilder addPublisher(Publisher publisher) {
            this.publisher = publisher;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        if (!UtilProvider.isEmpty(title))
            this.title = title;
    }

    public void setAuthor(String author) {
        if (!UtilProvider.isEmpty(author))
            this.author = author;
    }

    public void setIsbn(String isbn) {
        if (!UtilProvider.isEmpty(isbn))
            this.isbn = isbn;
    }

    public void setCopiesNumber(int copiesNumber) {
        this.copiesNumber = copiesNumber;
    }

    public void setDateOfPublication(Date dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}