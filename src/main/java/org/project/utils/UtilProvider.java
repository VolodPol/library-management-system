package org.project.utils;

import java.sql.Timestamp;

public class UtilProvider {
    public static Timestamp toTimestamp(String value) {
        return Timestamp.valueOf(value.replace('T', ' ').concat(":00"));
    }

    public static boolean isNotEmpty(String object) {
        return object != null && !object.isEmpty();
    }
}
