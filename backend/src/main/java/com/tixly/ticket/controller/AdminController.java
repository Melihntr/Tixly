package com.tixly.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.models.request.LoginRequest;
import com.tixly.ticket.models.response.LoginResponse;
import com.tixly.ticket.services.AdminDomainService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminDomainService adminDomainService;

    // Constructor injection
    @Autowired
    public AdminController(AdminDomainService adminDomainService) {
        this.adminDomainService = adminDomainService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String authKey = adminDomainService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(authKey, "User Logged in Successfully"));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authKey) {
        try {
            adminDomainService.logout(authKey);
            return ResponseEntity.status(HttpStatus.OK).body("Bye, user");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}