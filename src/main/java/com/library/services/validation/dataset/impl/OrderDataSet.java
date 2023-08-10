package com.library.services.validation.dataset.impl;

import com.library.services.validation.dataset.DataSet;
import com.library.utils.UtilProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The class contains order data to validate
 */
@AllArgsConstructor
@Getter
@Setter
public class OrderDataSet implements DataSet {
    private String startTime;
    private String endTime;
    private String orderType;
    private String userSub;

    @Override
    public boolean isBlank() {
        return UtilProvider.isEmpty(startTime) && UtilProvider.isEmpty(endTime) && UtilProvider.isEmpty(orderType) && UtilProvider.isEmpty(userSub);
    }
}
