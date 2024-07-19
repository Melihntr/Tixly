package com.tixly.ticket.controller;

/*import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.entity.Customer;
import com.tixly.ticket.services.CustomerService;

@RestController
@RequestMapping("/api")
public class RegisterController {
@Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody Customer customer) {
        // Validate the input using CustomerService
        if (!customerService.isValid(customer)) {
            return new ResponseEntity<>("Invalid username, password, or email, or username/email already exists.", HttpStatus.BAD_REQUEST);
        }

        // Save the customer and set the default status (e.g., pending)
        customer.setStatus("PENDING"); // Set default status
        customerService.createCustomer(customer);
        
        // Generate verification code and send/store it
        String verificationCode = customerService.generateVerificationCode();
        customerService.updateVerificationCode(customer.getUsername(),verificationCode);
        

        // Store or send verificationCode to user

        // Return a message indicating successful registration with the verification code
        String responseMessage = String.format("Customer registered successfully. Your verification code is: %s."+ '\n' +"Your auth key is: %s",
                verificationCode, customer.getAuthKey());

        // Return response with CREATED status
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }
}
*/