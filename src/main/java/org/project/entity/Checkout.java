package org.project.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class Checkout extends Entity {
    private int id;
    private Timestamp startTime;
    private Timestamp endTime;
    private byte isReturned;
    private OrderStatus orderStatus;
    private Type type;
    private byte finedStatus;
    private User user;
    private Book book;

    public Checkout(){}

    public Checkout(Timestamp startTime, Timestamp endTime, Type type, User user, Book book) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.user = user;
        this.book = book;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    @SuppressWarnings("unused")
    public byte getIsReturned() {
        return isReturned;
    }
    public void setIsReturned(byte isReturned) {
        this.isReturned = isReturned;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    public byte getFinedStatus() {
        return finedStatus;
    }
    public void setFinedStatus(byte finedStatus) {
        this.finedStatus = finedStatus;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Checkout checkout = (Checkout) o;

        if (id != checkout.id) return false;
        if (isReturned != checkout.isReturned) return false;
        if (!Objects.equals(startTime, checkout.startTime)) return false;
        if (!Objects.equals(endTime, checkout.endTime)) return false;
        if (orderStatus != checkout.orderStatus) return false;
        if (!Objects.equals(user, checkout.user)) return false;
        return Objects.equals(book, checkout.book);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (int) isReturned;
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (book != null ? book.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Checkout{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isReturned=" + isReturned +
                ", user=" + user +
                ", book=" + book +
                '}';
    }
}
