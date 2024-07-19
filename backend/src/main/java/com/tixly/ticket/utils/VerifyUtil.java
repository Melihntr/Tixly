package com.tixly.ticket.utils;

import java.util.Random;

import org.springframework.stereotype.Component;


@Component
public class VerifyUtil {

    private String storedCode;
    
// Method to generate a 6-digit random verification code
    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        storedCode = String.valueOf(code); // Store the generated code
        return storedCode;
    }
}
