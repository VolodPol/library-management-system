package org.project.entity;


public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private String coverage;
    private int copiesNumber;

    private Category category;
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

    public String getCoverage() {
        return coverage;
    }
    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public int getCopiesNumber() {
        return copiesNumber;
    }
    public void setCopiesNumber(int copiesNumber) {
        this.copiesNumber = copiesNumber;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
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
        return id == book.id && copiesNumber == book.copiesNumber
                && title.equals(book.title)
                && author.equals(book.author)
                && isbn.equals(book.isbn) && coverage.equals(book.coverage)
                && category.equals(book.category) && publisher.equals(book.publisher);
    }

    @Override
    public int hashCode() {
        int result = 13;

        result += 31 + result + id;
        result += 31 + result + (title != null ? title.hashCode() : 0);
        result += 31 + result + (author != null ? author.hashCode() : 0);
        result += 31 + result + (isbn != null ? isbn.hashCode() : 0);
        result += 31 + result + (coverage != null ? coverage.hashCode() : 0);
        result += 31 + result + copiesNumber;
        result += 31 + result + (category != null ? category.hashCode() : 0);
        result += 31 + result + (publisher != null ? publisher.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", coverage='" + coverage + '\'' +
                ", copiesNumber=" + copiesNumber +
                ", category=" + category +
                ", publisher=" + publisher +
                '}';
    }
}
