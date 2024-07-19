package com.tixly.ticket.services;

import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.tixly.ticket.entity.ECustomer;

//public class CustomerDomainService {
    /**
     * @summary: Returns updated customer's username
     * @return
     */
   /* public String resetPassword(){
        
         Customer customer = customerService.findByUsername(username);

        if (customer == null || !verificationCode.equals(customer.getVerificationCode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or verification code.");
            throw new ApplicationContextException("Invalid username or verification code.",  );
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
        return username;
    }
}
*/