package org.project.entity.dto;

import java.sql.Timestamp;
import java.util.Objects;

public class OrderedBookDTO {
    private int bookId;
    private String title;
    private String author;
    private Timestamp orderDate;
    private Timestamp returnDate;
    private String finedStatus;
    private String subscription;
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
    public Timestamp getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }
    @SuppressWarnings("unused")
    public Timestamp getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }
    @SuppressWarnings("unused")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedBookDTO that = (OrderedBookDTO) o;

        if (bookId != that.bookId) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(author, that.author)) return false;
        if (!Objects.equals(orderDate, that.orderDate)) return false;
        if (!Objects.equals(returnDate, that.returnDate)) return false;
        if (!Objects.equals(finedStatus, that.finedStatus)) return false;
        return Objects.equals(subscription, that.subscription);
    }

    @Override
    public int hashCode() {
        int result = bookId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        result = 31 * result + (finedStatus != null ? finedStatus.hashCode() : 0);
        result = 31 * result + (subscription != null ? subscription.hashCode() : 0);
        return result;
    }
}
