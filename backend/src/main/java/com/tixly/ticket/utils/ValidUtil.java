package com.tixly.ticket.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ValidUtil {
    @Autowired
    private JdbcTemplate jdbcTemplate;
// Regex patterns for email validation
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Method to check if email is valid
    public boolean isEmailValid(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
   
    public boolean isValidPassword(String password) {
        return password != null && password.length() >= RuleBase.MIN_PASSWORD_LENGTH && password.length() <= RuleBase.MAX_PASSWORD_LENGTH;
    }
    // Method to check if username or email already exists
    public boolean doesUsernameOrEmailExist(String username, String email) {
        String sql = "SELECT COUNT(*) FROM customer WHERE username = ? OR mail = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username, email}, Integer.class);
        return count != null && count > 0;
    }
    public boolean isValid(String username, String password, String mail) {
        return username != null && username.length() >= RuleBase.MIN_USERNAME_LENGTH && username.length() <= RuleBase.MAX_USERNAME_LENGTH
                && isValidPassword(password)
                && isEmailValid(mail)
                && !doesUsernameOrEmailExist(username, mail);
    }
}
