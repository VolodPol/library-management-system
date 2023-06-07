package com.library.entity.dto;

import com.library.entity.OrderStatus;

import java.sql.Timestamp;

public class CheckoutDTO {
    private int id;
    private Timestamp startTime;
    private Timestamp endTime;
    private String username;
    private String bookTitle;
    private OrderStatus orderStatus;
    private String type;
    private String finedStatus;
    private int bookId;
    @SuppressWarnings("unused")
    public Timestamp getStartTime() {
        return startTime;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
    @SuppressWarnings("unused")
    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    @SuppressWarnings("unused")
    public String getBookTitle() {
        return bookTitle;
    }
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @SuppressWarnings("unused")
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(OrderStatus orderStatus) {this.orderStatus = orderStatus;}

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    @SuppressWarnings("unused")
    public String getFinedStatus() {
        return finedStatus;
    }
    public void setFinedStatus(String finedStatus) {
        this.finedStatus = finedStatus;
    }
    @SuppressWarnings("unused")
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
