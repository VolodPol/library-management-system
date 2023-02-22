package org.project.entity.dto;

import java.sql.Date;

public class BookDTO {
    private String title;
    private String author;
    private String isbn;
    private int copiesNumber;
    private Date dateOfPublication;
    private String publisher;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    @SuppressWarnings("unused")
    public int getCopiesNumber() {
        return copiesNumber;
    }
    public void setCopiesNumber(int copiesNumber) {
        this.copiesNumber = copiesNumber;
    }
    @SuppressWarnings("unused")
    public Date getDateOfPublication() {
        return dateOfPublication;
    }
    public void setDateOfPublication(Date dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public BookDTO() {
    }
}
