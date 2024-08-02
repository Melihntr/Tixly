package com.tixly.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.services.CompanyDomainService;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyDomainService companyDomainService;

    @GetMapping()
    public ResponseEntity<?> getCompany(@RequestHeader(HttpHeaders.AUTHORIZATION) String authKey) {
        try {

            return new ResponseEntity<>(companyDomainService.getCompany(authKey), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
