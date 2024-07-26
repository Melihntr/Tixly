package com.tixly.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.models.dto.ProvinceModel;
import com.tixly.ticket.services.LocationDomainService;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationDomainService locationDomainService;

    @Autowired
    public LocationController(LocationDomainService locationDomainService) {
        this.locationDomainService = locationDomainService;
    }

    @GetMapping("/provinces")
    public ResponseEntity<Object> getAllProvinces() {
        try {
            List<ProvinceModel> provinces = locationDomainService.getAllProvinces();
            return new ResponseEntity<>(provinces, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Database error occurred while fetching provinces.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
