package com.library.utils;

import java.sql.Timestamp;

/**
 * The class provides small operations(utils) for convenience purposes.
 */
public class UtilProvider {
    public static Timestamp toTimestamp(String value) {
        return Timestamp.valueOf(value.replace('T', ' ').concat(":00"));
    }

    public static boolean isEmpty(String object) {
        return object == null || object.isEmpty();
    }

    public static String isFined(int finedStatus) {
        return finedStatus == 1 ? "50₴" : "No fine";
    }

    public static String getLastPage(String value) {
        return (value == null ? "" : "&page=".concat(value));
    }

    public static String getFineCookie(String login) {
        return "fines".concat("_" + login);
    }
}
