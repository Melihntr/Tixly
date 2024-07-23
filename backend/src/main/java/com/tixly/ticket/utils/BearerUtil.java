package com.tixly.ticket.utils;

public class BearerUtil {

    public static String extractToken(String authKey) {
        if (authKey == null || !authKey.contains("Bearer ")) {
            throw new IllegalArgumentException("Invalid authKey: missing 'Bearer ' prefix.");
        }
        return authKey.replaceAll("Bearer ", "");
    }
}