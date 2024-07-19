package com.tixly.ticket.controller;

/*import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.entity.Customer;
import com.tixly.ticket.services.CustomerService;

@RestController
@RequestMapping("/api")
public class VerifyCodeController {
    @Autowired
    private CustomerService customerService;
@PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> requestBody) {
        String userInput = requestBody.get("userInput");

        if (userInput == null || userInput.isEmpty()) {
            return ResponseEntity.badRequest().body("User input code is required.");
        }

        boolean isValid = customerService.verifyCode(userInput);

        if (isValid) {
            // Update customer status to active upon successful verification
            Customer customer = customerService.getCustomerByVerificationCode(userInput);
            if (customer != null) {
                customer.setStatus("ACTIVE"); // Set status to active
                customerService.activateCustomer(customer); // Update customer status
            }
            return ResponseEntity.ok("Verification successful! Customer is now active.");
        } else {
            return ResponseEntity.badRequest().body("Verification failed. Please try again.");
        }
    }
}*/
