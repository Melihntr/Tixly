package com.tixly.ticket.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.models.request.RegisterRequest;
import com.tixly.ticket.services.RegisterDomainService;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterDomainService registerDomainService;

    @PostMapping()
public ResponseEntity<String> registerCustomer(@RequestBody RegisterRequest registerRequest) {
    try {
        String responseMessage = registerDomainService.register(
                registerRequest.getUsername(),
                registerRequest.getPassword(),
                registerRequest.getMail(),
                registerRequest.getGender(),
                registerRequest.getTcNo(), 
                registerRequest.getPhoneNumber()
        );
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @PostMapping("/verifyCode")
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> requestBody) {
        try {
            String userInput = requestBody.get("userInput");
            String responseMessage = registerDomainService.verifyCode(userInput);
            return ResponseEntity.ok(responseMessage);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
