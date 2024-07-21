package com.tixly.ticket.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ValidUtil {
    
// Regex patterns for email validation
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(RuleBase.EMAIL_REGEX);

    // Method to check if email is valid
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
