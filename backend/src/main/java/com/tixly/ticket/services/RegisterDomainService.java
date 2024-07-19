package com.tixly.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.ECustomer;
import com.tixly.ticket.utils.JwtUtil;
import com.tixly.ticket.utils.ValidUtil;
import com.tixly.ticket.utils.VerifyUtil;

@Service
public class RegisterDomainService {

    @Autowired
    private EntityService entityService;
    @Autowired
    private VerifyUtil verifyutil;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ValidUtil ValidUtil;

    public String register(String username, String password, String mail, String gender) throws IllegalArgumentException {
        
       
        // Validate the input using EntityService
        ECustomer customerEntity = entityService.getCustomer();
        if (ValidUtil.doesUsernameOrEmailExist(username, mail)) {
            throw new IllegalArgumentException("Username or email already exists.");
        }
        // Validate the input fields
        if (!ValidUtil.isValid(username,password,mail)) {
            throw new IllegalArgumentException("Invalid username, password, or email.");
        }
        // Save the customer and set the default status (e.g., pending)
        customerEntity.createCustomer(username,password,mail,gender);
        // Generate verification code and send/store it
        String verificationCode = verifyutil.generateVerificationCode();
        customerEntity.updateVerificationCode(username, verificationCode);
        // Store or send verification code to user
        // You might want to send an email or SMS here, but for simplicity, we'll assume it's stored
        // Return a message indicating successful registration with the verification code
        String jwtToken = jwtUtil.generateToken(username);
        customerEntity.updateAuthKey(username,jwtToken);
        return String.format("Customer registered successfully. Your verification code is: %s.\nYour auth key is: %s",
                verificationCode, jwtToken);
    }

    public String verifyCode(String userInput) {
        ECustomer customerEntity = entityService.getCustomer();
        if (userInput == null || userInput.isEmpty()) {
            return "User input code is required.";
        }

        boolean isValid = customerEntity.verifyCode(userInput);

        if (isValid) {
            // Update customer status to active upon successful verification
             
             return "Verification successful! Customer is now active.";
             
        } else {
            return "Verification failed. Please try again.";
        }
    }
}
