package org.project.services.validation;

import org.project.entity.Type;
import org.project.utils.UtilProvider;

import java.sql.Timestamp;
import java.util.Calendar;

public class OrderValidator {
    private static final String HOURS_PATTERN = " \\d{2}:\\d{2}:\\d{2}.\\d+";
    private static final int MAX_DAYS = 92;

    /*
        В читацьку залу - максимум 1 день (10:00 - 18:00)
        На підписку - максимум 3 місяці
        старт раніше кінця
     */
    public static boolean validateOrderTime(String start, String end, Type type) {
        Timestamp startTime = UtilProvider.toTimestamp(start);
        Timestamp endTime = UtilProvider.toTimestamp(end);

        Timestamp currentMoment = new Timestamp(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.DAY_OF_MONTH, MAX_DAYS);

        if (type.equals(Type.READING_ROOM)) {
            return (startTime.after(getShiftTime(currentMoment, true))
                    && (endTime.before(getShiftTime(currentMoment, false))));
        } else {
            return (calendar.getTime().after(endTime));
        }
    }

    public static boolean validateEndAfter(String start, String end) {
        Timestamp startTime = UtilProvider.toTimestamp(start);
        Timestamp endTime = UtilProvider.toTimestamp(end);
        return endTime.after(startTime);
    }

    private static Timestamp getShiftTime(Timestamp now, boolean isStart) {
        String value = now
                .toString()
                .replaceFirst(HOURS_PATTERN, isStart ? " 10:00:00.0" : " 18:00:00.0");
        return Timestamp.valueOf(value);
    }
}

