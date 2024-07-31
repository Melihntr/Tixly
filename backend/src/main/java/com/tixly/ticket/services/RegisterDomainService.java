package com.tixly.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.User;
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

    public String register(String username, String password, String mail, String gender, String tcNo, String phoneNumber) throws IllegalArgumentException {

        if (!ValidUtil.isValidUsername(username) || !ValidUtil.isValidPassword(password) || !ValidUtil.isEmailValid(mail)) {
            throw new IllegalArgumentException("Invalid username, password, or email.");
        }
       
        User userEntity = entityService.getCustomer();
        if (userEntity.IsUserExist(username, mail)) {
            throw new IllegalArgumentException("Username or email already exists.");
        }
    
        userEntity.createCustomer(username, password, mail, gender, tcNo, phoneNumber);     
        String verificationCode = verifyutil.generateVerificationCode();
        userEntity.updateVerificationCode(username, verificationCode);
        String jwtToken = jwtUtil.generateToken(username);
        userEntity.updateAuthKey(username, jwtToken);
        
        System.out.println("Your verification code is " + verificationCode);
        return String.format("Customer registered successfully.\nYour auth key is: %s", jwtToken);
    }


    public String verifyCode(String userInput) {
        if (userInput == null || userInput.isEmpty()) {
             throw new IllegalArgumentException("User input code is required.");
        }
        User userEntity = entityService.getCustomer();
        if (userEntity.verifyCode(userInput)) {

            return "Verification successful! Customer is now active.";

        } else {
            throw new IllegalArgumentException("Verification failed. Please try again.");
        }
    }
}
