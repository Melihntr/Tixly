package com.tixly.ticket.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ValidUtil {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(RuleBase.EMAIL_REGEX);
    private static final Pattern PLATE_PATTERN = Pattern.compile(RuleBase.PLATE_PATTERN);

    public boolean isValidPlate(String plateNo) {
        Matcher matcher = PLATE_PATTERN.matcher(plateNo);
        if (matcher.matches()) {
            int firstPart = Integer.parseInt(matcher.group(1));

            if (firstPart > 81 || firstPart<01) {
                return false;
            }
            return true;
        }
        return false;
    }
    public void validateBusType(String busType,int seatNo,String plateNo) {
        if (!busType.equals(RuleBase.TYPE_2S1) && !busType.equals(RuleBase.TYPE_2S2)) {
            throw new IllegalArgumentException("Invalid bus type. Must be '" + RuleBase.TYPE_2S1 + "' or '" + RuleBase.TYPE_2S2 + "'.");
        }
        if (seatNo % 3 != 0 && seatNo % 4 != 0) {
            throw new IllegalArgumentException("Invalid seat number. Must be a multiple of 3 or 4.");
        }
        if (!isValidPlate(plateNo)) {
            throw new IllegalArgumentException("Invalid plate number format.");
        }
    }
    public boolean isEmailValid(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
   
    public boolean isValidPassword(String password) {
        return password != null && password.length() >= RuleBase.MIN_PASSWORD_LENGTH && password.length() <= RuleBase.MAX_PASSWORD_LENGTH;
    }
    
    public boolean isValidUsername(String username) {
        return username != null && username.length() >= RuleBase.MIN_USERNAME_LENGTH && username.length() <= RuleBase.MAX_USERNAME_LENGTH;         
    }
}
