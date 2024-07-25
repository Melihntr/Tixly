package com.tixly.ticket.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.tixly.ticket.models.request.TripRequest;
import com.tixly.ticket.services.TripDomainService;

import jakarta.security.auth.message.callback.PrivateKeyCallback;

@RestController
@RequestMapping("/trip")
public class TripController {

    @Autowired
    private TripDomainService tripDomainService;

    @PostMapping("/register")
    public ResponseEntity<String> registerTrip(@RequestBody TripRequest tripRequest) {
                try {
                    tripDomainService.registerTrip(
                        tripRequest.getId(),
                        tripRequest.getPeronNo(),
                        tripRequest.getDepartureLocationId(),
                        tripRequest.getArrivalLocationId(),
                        tripRequest.getEstimatedTime(),
                        tripRequest.getPrice(),
                        tripRequest.getCompanyId(),
                        tripRequest.getBusId(),
                        tripRequest.getDepartureTime()
                    );
            return new ResponseEntity<>("Trip registered successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/cancel/{tripId}")
    public ResponseEntity<String> cancelTrip(@PathVariable Long tripId) {
        try {
            tripDomainService.deleteTrip(tripId);
            return new ResponseEntity<>("Trip canceled successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
