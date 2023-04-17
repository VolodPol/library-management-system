package org.project.services.validation.dataset.impl;

import org.project.services.validation.dataset.DataSet;
import static org.project.utils.UtilProvider.isEmpty;

/**
 * The class contains book data to validate
 */
public class BookDataSet implements DataSet {
    private String title;
    private String author;
    private String isbn;
    private String copies;
    private String dateOfPublication;
    private String publisher;
    public BookDataSet(String title, String author, String isbn, String copies, String dateOfPublication, String publisher) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.copies = copies;
        this.dateOfPublication = dateOfPublication;
        this.publisher = publisher;
    }

    @Override
    public boolean isBlank() {
        return isEmpty(title) && isEmpty(author)
                && isEmpty(isbn) && isEmpty(copies)
                && isEmpty(dateOfPublication) && isEmpty(publisher);
    }

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

    public String getCopies() {
        return copies;
    }
    @SuppressWarnings("unused")
    public void setCopies(String copies) {
        this.copies = copies;
    }

    public String getDateOfPublication() {
        return dateOfPublication;
    }
    @SuppressWarnings("unused")
    public void setDateOfPublication(String dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
