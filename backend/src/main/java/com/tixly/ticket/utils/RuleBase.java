package com.tixly.ticket.utils;

public class RuleBase {
    public static final int MIN_USERNAME_LENGTH = 4;
    public static final int MAX_USERNAME_LENGTH = 50;
    public static final int MIN_PASSWORD_LENGTH = 4;
    public static final int MAX_PASSWORD_LENGTH = 50;
    public static final int SECRET_KEY_LENGTH = 64;
    public static final int MIN_AUTHKEY_LENGTH = 4;
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String PLATE_PATTERN = "(^\\d{2})[A-Z]{3}\\d{3}$";
    public static final String TYPE_2S1 = "2s1";
    public static final String TYPE_2S2 = "2s2";
    public static final String[] VALID_BUS_TYPES = { TYPE_2S1, TYPE_2S2 };
}
