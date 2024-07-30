package com.tixly.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tixly.ticket.models.dto.UserInfo;
import com.tixly.ticket.services.UserDomainService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDomainService userService;

    @GetMapping()
     public UserInfo getUserInfo(@RequestHeader("Authorization") String authHeader) {
        try {
            return userService.getUserInfoByAuthKey(authHeader);
        } 
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Data access error occurred.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
        }
    }
}