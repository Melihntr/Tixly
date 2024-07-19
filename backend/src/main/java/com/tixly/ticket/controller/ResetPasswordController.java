package com.tixly.ticket.controller;

/*import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.entity.Customer;
import com.tixly.ticket.models.request.PasswordResetRequest;
import com.tixly.ticket.services.CustomerService;

@RestController
@RequestMapping("/api")
public class ResetPasswordController {
    @Autowired
    private CustomerService customerService;
// Endpoint to reset the password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request) {
        String username = request.getUsername();
        String verificationCode = request.getVerificationCode();
        String newPassword = request.getNewPassword();

        if (username == null || username.isEmpty() || verificationCode == null || verificationCode.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("Username, verification code, and new password are required.");
        }

        Customer customer = customerService.findByUsername(username);

        if (customer == null || !verificationCode.equals(customer.getVerificationCode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or verification code.");
        }

        // Validate the new password (length, complexity, etc.)
        if (!customerService.isValidPassword(newPassword)) {
            return ResponseEntity.badRequest().body("Invalid new password. Ensure it meets the complexity requirements.");
        }

        // Update the customer's password
        customerService.updatePassword(username, newPassword);

        // Clear the verification code
        customerService.updateVerificationCode(username, null);

        // Return a message indicating successful password reset
        return ResponseEntity.ok("Password reset successfully for " + username);
    }
}
*/