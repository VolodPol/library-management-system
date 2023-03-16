package org.project.services.validation.impl;

import org.junit.jupiter.api.Test;
import org.project.entity.Subscription;
import org.project.entity.Type;
import org.project.services.resources.MessageName;
import org.project.services.validation.dataset.impl.OrderDataSet;

import java.sql.Timestamp;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class OrderValidatorTest {
    private OrderValidator validator;
    private OrderDataSet dataSet;

    @Test
    public void validate_Blank_Case() {
        dataSet = new OrderDataSet(
                "", "", "", ""
        );
        validator = new OrderValidator(dataSet);
        assertFalse(validator.validate());
        assertEquals(MessageName.EMPTY_FORM, validator.getErrorMessage());
    }

    @Test
    public void validate_No_Sub() {
        dataSet = new OrderDataSet(
                "", "", Type.SUBSCRIPTION.toString(), Subscription.BASIC.toString()
        );
        validator = new OrderValidator(dataSet);
        assertFalse(validator.validate());
        assertEquals(MessageName.NO_SUB, validator.getErrorMessage());
    }

    @Test
    public void validate_ReadRoom_OutOfShift() {
        dataSet = new OrderDataSet(
                new Timestamp(System.currentTimeMillis()).toString().replaceFirst(" (\\d*[:.]\\d*)*", "T8:05"),
                new Timestamp(System.currentTimeMillis()).toString().replaceFirst(" (\\d*[:.]\\d*)*", "T20:30"),
                Type.READING_ROOM.toString(),
                Subscription.BASIC.toString()
        );
       validator = new OrderValidator(dataSet);
       assertFalse(validator.validate());
       assertEquals(MessageName.OUT_OF_SHIFT, validator.getErrorMessage());
    }

    @Test
    public void validate_Max_Time() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.DAY_OF_MONTH, 100);

        dataSet = new OrderDataSet(
                new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 2).toString().replaceFirst(" (\\d*[:.]\\d*)*", "T8:05"),
                new Timestamp(calendar.getTimeInMillis()).toString().replaceFirst(" (\\d*[:.]\\d*)*", "T8:05"),
                Type.SUBSCRIPTION.toString(),
                Subscription.READER.toString()
        );
        validator = new OrderValidator(dataSet);
        assertFalse(validator.validate());
        assertEquals(MessageName.MAX_TIME, validator.getErrorMessage());
    }

    @Test
    public void validate_Too_Early() {
        String start = new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60 * 5).toString()
                .replaceFirst(" ", "T")
                .replaceFirst(":\\d{1,2}[.]\\d*", "");
        String end = new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60).toString()
                .replaceFirst(" ", "T")
                .replaceFirst(":\\d{1,2}[.]\\d*", "");
        dataSet = new OrderDataSet(
                start,
                end,
                Type.SUBSCRIPTION.toString(),
                Subscription.READER.toString()
        );
        validator = new OrderValidator(dataSet);
        assertFalse(validator.validate());
        assertEquals(MessageName.EARLY_RETURN, validator.getErrorMessage());
    }
    @Test
    public void validate_Correct_Input() {
        String start = new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 5).toString()
                .replaceFirst(" ", "T")
                .replaceFirst(":\\d{1,2}[.]\\d*", "");
        String end = new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24 + 10).toString()
                .replaceFirst(" ", "T")
                .replaceFirst(":\\d{1,2}[.]\\d*", "");
        dataSet = new OrderDataSet(
                start,
                end,
                Type.SUBSCRIPTION.toString(),
                Subscription.READER.toString()
        );
        validator = new OrderValidator(dataSet);
        assertTrue(validator.validate());
        assertNull(validator.getErrorMessage());
    }
}