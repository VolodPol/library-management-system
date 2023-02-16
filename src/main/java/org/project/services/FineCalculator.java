package org.project.services;

import jakarta.servlet.http.Cookie;
//actual - НАСПРАВДІ, last - ОСТАННЄ ЗАПИС з КУКІ
public class FineCalculator {
    /* returns real fine amount */
    public static int calculate(String username, Cookie[] cookies, int actualNum, int oldAmount) {
        int lastRec = 0;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("fines".concat("_" + username)) && !cookie.getValue().isEmpty()) {
                lastRec = Integer.parseInt(cookie.getValue());
            }
        }
        if (lastRec == 0 && oldAmount > 0) return 0;
        return (actualNum - lastRec) * 50;
    }
}
