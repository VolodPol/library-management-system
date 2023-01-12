package org.project.entity;


import java.sql.Date;
import java.util.Objects;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private int copiesNumber;
    private Date dateOfPublication;

    private Publisher publisher;


    public Book() {

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public int getCopiesNumber() {
        return copiesNumber;
    }
    public void setCopiesNumber(int copiesNumber) {
        this.copiesNumber = copiesNumber;
    }

    public Date getDateOfPublication() {
        return dateOfPublication;
    }
    public void setDateOfPublication(Date dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public Publisher getPublisher() {
        return publisher;
    }
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (copiesNumber != book.copiesNumber) return false;
        if (!Objects.equals(title, book.title)) return false;
        if (!Objects.equals(author, book.author)) return false;
        if (!Objects.equals(isbn, book.isbn)) return false;
        if (!Objects.equals(dateOfPublication, book.dateOfPublication))
            return false;
        return Objects.equals(publisher, book.publisher);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + copiesNumber;
        result = 31 * result + (dateOfPublication != null ? dateOfPublication.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", copiesNumber=" + copiesNumber +
                ", dateOfPublication=" + dateOfPublication +
                ", publisher=" + publisher +
                '}';
    }
}