package org.project.utils;

import java.sql.Timestamp;

public class FineChecker {
    public static String compareTime(Timestamp expiration) {
        String resultMessage;
        Timestamp now = new Timestamp(System.currentTimeMillis());

        int flag = expiration.compareTo(now);
        if (flag > 0) {
            resultMessage = "Alright";
        } else if (flag < 0) {
            resultMessage = "Fined!";
        } else {
            resultMessage = "Hurry up!";
        }
        return resultMessage;
    }
}
