package org.project.services.validation.impl;

import org.project.entity.Subscription;
import org.project.entity.Type;
import org.project.services.resources.MessageName;
import org.project.services.validation.Validator;
import org.project.services.validation.dataset.impl.OrderDataSet;
import org.project.utils.UtilProvider;

import java.sql.Timestamp;
import java.util.Calendar;

public class OrderValidator implements Validator {
    private final OrderDataSet dataSet;
    private String errorMessage;

    private static final String HOURS_PATTERN = " \\d{2}:\\d{2}:\\d{2}.\\d+";
    private static final int MAX_DAYS = 92;

    /*
        В читацьку залу - максимум 1 день (10:00 - 18:00)
        На підписку - максимум 3 місяці
        старт раніше кінця
     */
    public OrderValidator(OrderDataSet dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public boolean validate() {
        if (dataSet.isBlank()) {
            errorMessage = MessageName.EMPTY_FORM;
            return false;
        }
        return (validateSubscription(dataSet.getUserSub(), dataSet.getOrderType())
                && validateOrderTime(dataSet.getStartTime(), dataSet.getEndTime(), Type.valueOf(dataSet.getOrderType().toUpperCase()))
                && validateEndAfter(dataSet.getStartTime(), dataSet.getEndTime()));
    }
    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    private boolean validateSubscription(String subscription, String orderType) {
        Subscription sub = Subscription.valueOf(subscription.toUpperCase());
        Type type = Type.valueOf(orderType.toUpperCase());

        if (sub.equals(Subscription.BASIC) && type.equals(Type.SUBSCRIPTION)) {
            errorMessage = MessageName.NO_SUB;
            return false;
        }
        return true;
    }

    private boolean validateOrderTime(String start, String end, Type type) {
        boolean readResult = true;
        boolean subResult = true;

        Timestamp startTime = UtilProvider.toTimestamp(start);
        Timestamp endTime = UtilProvider.toTimestamp(end);

        if (type.equals(Type.READING_ROOM)) {
            Timestamp currentMoment = new Timestamp(System.currentTimeMillis());
            readResult = (startTime.after(getShiftTime(currentMoment, true))
                    && (endTime.before(getShiftTime(currentMoment, false))));
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTime);
            calendar.add(Calendar.DAY_OF_MONTH, MAX_DAYS);
            subResult = (calendar.getTime().after(endTime));
        }
        if (!readResult)
            errorMessage = MessageName.OUT_OF_SHIFT;
        else if (!subResult)
            errorMessage = MessageName.MAX_TIME;
        return (readResult && subResult);
    }

    private boolean validateEndAfter(String start, String end) {
        boolean result;

        Timestamp startTime = UtilProvider.toTimestamp(start);
        Timestamp endTime = UtilProvider.toTimestamp(end);

        result = endTime.after(startTime) && startTime.after(new Timestamp(System.currentTimeMillis()));
        if (!result)
            errorMessage = MessageName.EARLY_RETURN;
        return result;
    }

    private Timestamp getShiftTime(Timestamp now, boolean isStart) {
        String value = now
                .toString()
                .replaceFirst(HOURS_PATTERN, isStart ? " 10:00:00.0" : " 18:00:00.0");
        return Timestamp.valueOf(value);
    }
}

