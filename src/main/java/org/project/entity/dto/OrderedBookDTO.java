package org.project.entity.dto;

import java.sql.Timestamp;

public class OrderedBookDTO {
    private int bookId;
    private String title;
    private String author;
    private Timestamp orderDate;
    private Timestamp returnDate;
    private String finedStatus;
    private String subscription;

    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
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

    public Timestamp getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    public String getFinedStatus() {
        return finedStatus;
    }
    public void setFinedStatus(String finedStatus) {
        this.finedStatus = finedStatus;
    }

    public String getSubscription() {
        return subscription;
    }
    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }
}
