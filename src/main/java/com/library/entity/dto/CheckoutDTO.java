package com.library.entity.dto;

import com.library.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
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
}
