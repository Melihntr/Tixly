package com.tixly.ticket.controller;

/*import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.entity.Customer;
import com.tixly.ticket.models.request.VerificationRequest;
import com.tixly.ticket.services.CustomerService;

@RestController
@RequestMapping("/api")
public class SendCodeController {
    @Autowired
    private CustomerService customerService;
@PostMapping("/send-code")
    public ResponseEntity<String> generateVerificationCode(@RequestBody VerificationRequest request) {
        String username = request.getUsername();
        String email = request.getMail();

        if (username == null || username.isEmpty() || email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Username and email are required.");
        }

        Customer customer = customerService.findByUsername(username);

        if (customer == null || !customer.getmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found with username: " + username + " and email: " + email);
        }

        // Generate verification code
        String verificationCode = customerService.generateVerificationCode();

        // Store verification code in the database or send it via email, SMS, etc.
        customer.setVerificationCode(verificationCode);
        customerService.updateVerificationCode(customer.getUsername(), verificationCode);

        // Return a message indicating successful generation of verification code
        return ResponseEntity.ok("Verification code generated successfully for " + username + ". Code: " + verificationCode);
    }
}
*/