package com.tixly.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.User;
import com.tixly.ticket.utils.ValidUtil;
import com.tixly.ticket.utils.VerifyUtil;

@Service
public class ResetPasswordDomainService {

    @Autowired
    private EntityService entityService;
    @Autowired
    private ValidUtil ValidUtil;
    @Autowired
    private VerifyUtil verifyUtil;

    public void resetPassword(String username, String verificationCode, String newPassword) {

        if (username == null || username.isEmpty() || verificationCode == null || verificationCode.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("Username, verification code, and new password are required.");
        }
        User userEntity = entityService.getCustomer();
        if (!userEntity.verifyCode(verificationCode)) {
            throw new IllegalArgumentException("Invalid username or verification code.");
        }

        if (!ValidUtil.isValidPassword(newPassword)) {
            throw new IllegalArgumentException("Invalid new password. Ensure it meets the complexity requirements.");
        }

        userEntity.updatePassword(username, newPassword);
        userEntity.updateVerificationCode(username, null);
        
    }

    public String generateVerificationCode(String username, String email) {
        if (!ValidUtil.isValidUsername(username)||!ValidUtil.isEmailValid(email)) {
            throw new IllegalArgumentException("Username and email are required.");
        }

        User userEntity = entityService.getCustomer();
        boolean exists = userEntity.IsUserExist(username, email);
        if (!exists) {
            throw new IllegalArgumentException("Customer not found with username: " + username + " and email: " + email);
        }

        String verificationCode = verifyUtil.generateVerificationCode();
        userEntity.updateVerificationCode(username, verificationCode);
        return String.format("Verification code generated successfully for %s. Code: %s", username, verificationCode);
    }
}
