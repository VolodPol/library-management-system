package com.library.entity;

import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
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

    public Checkout(CheckoutBuilder builder) {
        this.id = builder.id;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.type = builder.type;
        this.user = builder.user;
        this.book = builder.book;
    }

    @SuppressWarnings("all")
    public static final class CheckoutBuilder {
        private int id;
        private Timestamp startTime;
        private Timestamp endTime;
        private byte isReturned;
        private OrderStatus orderStatus;
        private Type type;
        private byte finedStatus;
        private User user;
        private Book book;

        public CheckoutBuilder addId(int id) {
            this.id = id;
            return this;
        }

        public CheckoutBuilder addStartTime(Timestamp startTime) {
            this.startTime = startTime;
            return this;
        }

        public CheckoutBuilder addEndTime(Timestamp endTime) {
            this.endTime = endTime;
            return this;
        }

        public CheckoutBuilder addIsReturned(byte isReturned) {
            this.isReturned = isReturned;
            return this;
        }

        public CheckoutBuilder addOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public CheckoutBuilder addType(Type type) {
            this.type = type;
            return this;
        }

        public CheckoutBuilder addFinedStatus(byte finedStatus) {
            this.finedStatus = finedStatus;
            return this;
        }

        public CheckoutBuilder addUser(User user) {
            this.user = user;
            return this;
        }

        public CheckoutBuilder addBook(Book book) {
            this.book = book;
            return this;
        }

        public Checkout build() {
            return new Checkout(this);
        }
    }
}
