package com.library.services.validation.dataset.impl;

import com.library.services.validation.dataset.DataSet;
import com.library.utils.UtilProvider;

/**
 * The class contains order data to validate
 */
public class OrderDataSet implements DataSet {
    private String startTime;
    private String endTime;
    private String orderType;
    private String userSub;

    public OrderDataSet(String startTime, String endTime, String orderType, String userSub) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.orderType = orderType;
        this.userSub = userSub;
    }

    @Override
    public boolean isBlank() {
        return UtilProvider.isEmpty(startTime) && UtilProvider.isEmpty(endTime) && UtilProvider.isEmpty(orderType) && UtilProvider.isEmpty(userSub);
    }

    public String getStartTime() {
        return startTime;
    }
    @SuppressWarnings("unused")
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    @SuppressWarnings("unused")
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrderType() {
        return orderType;
    }
    @SuppressWarnings("unused")
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getUserSub() {
        return userSub;
    }
    @SuppressWarnings("unused")
    public void setUserSub(String userSub) {
        this.userSub = userSub;
    }
}
