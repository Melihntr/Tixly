package com.tixly.ticket.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.models.request.PasswordResetRequest;
import com.tixly.ticket.models.request.VerificationRequest;
import com.tixly.ticket.services.AdminResetPasswordDomainService;
@RestController
@RequestMapping("/admin")
public class AdminResetPasswordController {

    @Autowired
    private AdminResetPasswordDomainService resetPasswordDomainService;

// Endpoint to reset the password
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request) {
        try {
            resetPasswordDomainService.resetPassword(
                    request.getUsername(),
                    request.getVerificationCode(),
                    request.getNewPassword()
            );
            return ResponseEntity.ok("Password reset successfully for " + request.getUsername());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @PostMapping("/send-code")
    public ResponseEntity<String> generateVerificationCode(@RequestBody VerificationRequest request) {
        try {
            String responseMessage = resetPasswordDomainService.generateVerificationCode(
                    request.getUsername(),
                    request.getMail()
            );
            return ResponseEntity.ok(responseMessage);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}