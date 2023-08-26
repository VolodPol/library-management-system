package com.library.services.validation.impl;

import com.library.entity.Subscription;
import com.library.entity.Type;
import com.library.services.validation.ValidationStrategy;
import com.library.utils.UtilProvider;
import com.library.services.resources.MessageName;
import com.library.services.validation.dataset.impl.OrderDataSet;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * The strategy class for order data validation.
 */
public class OrderStrategy implements ValidationStrategy {
    /**
     * Order data
     */
    private final OrderDataSet dataSet;
    /**
     * Encapsulated error message
     */
    private String errorMessage;

    private static final String HOURS_PATTERN = " \\d{2}:\\d{2}:\\d{2}.\\d+";
    private static final int MAX_DAYS = 92;

    public OrderStrategy(OrderDataSet dataSet) {
        this.dataSet = dataSet;
    }
    /**
        Reading room - maximum 1 day (10:00 - 18:00)
        On subscription - maximum 3 month
        Start before end
     **/
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

    /**
     * Validates subscription cases: user can't order book on a subscription
     * if the user has basic subscription.
     * @param subscription user's subscription
     * @param orderType type of order (on a subscription, to the reading room)
     * @return boolean as a validation result
     */
    private boolean validateSubscription(String subscription, String orderType) {
        Subscription sub = Subscription.valueOf(subscription.toUpperCase());
        Type type = Type.valueOf(orderType.toUpperCase());

        if (sub.equals(Subscription.BASIC) && type.equals(Type.SUBSCRIPTION)) {
            errorMessage = MessageName.NO_SUB;
            return false;
        }
        return true;
    }

    /**
     * Method to validate order time cases: time range to the reading room - 1 day maximum (10:00 - 18:00),
     * if on a subscription - then maximum period is 3 month (92 days).
     * @param start registration time
     * @param end time to bring back
     * @param type the type of order
     * @return boolean to confirm validation
     */
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

    /**
     * Checks if the registration time is after the current moments and
     * the time to bring back is after time of the registration.
     * @param start registration time
     * @param end time to bring back
     * @return boolean as the result of validation
     */
    private boolean validateEndAfter(String start, String end) {
        boolean result;

        Timestamp startTime = UtilProvider.toTimestamp(start);
        Timestamp endTime = UtilProvider.toTimestamp(end);

        result = endTime.after(startTime) && startTime.after(new Timestamp(System.currentTimeMillis()));
        if (!result)
            errorMessage = MessageName.EARLY_RETURN;
        return result;
    }

    /**
     * Helper method that provides the shift time of a current day.
     * @param now current moment
     * @param isStart boolean (
     *                true - start of the shift,
     *                false - end of the shift)
     * @return Timestamp of the shift
     */
    private Timestamp getShiftTime(Timestamp now, boolean isStart) {
        String value = now
                .toString()
                .replaceFirst(HOURS_PATTERN, isStart ? " 10:00:00.0" : " 18:00:00.0");
        return Timestamp.valueOf(value);
    }
}

