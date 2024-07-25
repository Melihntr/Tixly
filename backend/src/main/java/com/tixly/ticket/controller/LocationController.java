package com.tixly.ticket.controller;
/* 
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.entity.District;
import com.tixly.ticket.entity.Province;
import com.tixly.ticket.services.LocationDomainService;

@RestController
@RequestMapping("/location")
public class LocationController {


    @Autowired
    private LocationDomainService locationDomainService;
    
        public LocationController(LocationDomainService locationDomainService) {
            this.locationDomainService = locationDomainService;
        }

    @GetMapping("/provinces")
    public ResponseEntity<List<Province>> getProvinces() {
        try {
            List<Province> provinces = locationDomainService.getAllProvinces();
            return ResponseEntity.ok(provinces);
        } catch (Exception e) {
            // Handle error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.emptyList());
        }
    }
    
    @GetMapping("/districts")
    public ResponseEntity<?> getDistricts() {
        try {
            List<District> districts = locationDomainService.getAllDistricts();
            return new ResponseEntity<>(districts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while retrieving districts: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}*/