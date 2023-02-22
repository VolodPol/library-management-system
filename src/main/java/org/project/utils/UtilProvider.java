package org.project.utils;

import org.project.filters.PageNavigation;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.stream.Stream;

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

    public static boolean isAccessDenied(String path, PageNavigation role) {
        boolean result = false;
        Stream<String> routes = Arrays.stream(role.getRoutes()).
                filter(elem -> elem.equals(path));
        if (routes.findAny().isPresent())
            result = true;
        return !result;
    }
    public static String getFineCookie(String login) {
        return "fines".concat("_" + login);
    }
}
