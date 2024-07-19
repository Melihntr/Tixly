package com.tixly.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.ECustomer;
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
    public String resetPassword(String username, String verificationCode, String newPassword) {
        // Check if all parameters are provided
        if (username == null || username.isEmpty() || verificationCode == null || verificationCode.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            return "Username, verification code, and new password are required.";
        }
        // Find the customer by username
        ECustomer customerEntity = entityService.getCustomer();       
        // Check if customer exists and verification code matches
        if (!customerEntity.verifyCode(verificationCode)) {
            return "Invalid username or verification code.";
        }
        // Validate the new password
        if (!ValidUtil.isValidPassword(newPassword)) {
            return "Invalid new password. Ensure it meets the complexity requirements.";
        }
        // Update the customer's password
        customerEntity.updatePassword(username, newPassword);
        // Clear the verification code
        customerEntity.updateVerificationCode(username, null);
        // Return a success message
        return "Password reset successfully for " + username;
    }
    public String generateVerificationCode(String username, String email) {
        ECustomer customerEntity = entityService.getCustomer(); 
        boolean exists = customerEntity.doesUsernameAndEmailExist(username, email);
        if (!exists) {
            return "Customer not found with username: " + username + " and email: " + email;
        }
        // Check if the customer's email matches the provided email
            // Generate verification code
            String verificationCode = verifyUtil.generateVerificationCode();
            // Store verification code in the database  
            customerEntity.updateVerificationCode(username, verificationCode);
            // Return a message indicating successful generation of verification code
            return String.format("Verification code generated successfully for %s. Code: %s", username, verificationCode); 
    }
}
