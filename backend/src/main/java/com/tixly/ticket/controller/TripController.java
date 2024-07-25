package com.tixly.ticket.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.models.request.TripRequest;
import com.tixly.ticket.services.TripDomainService;

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
                        tripRequest.getBusId()
                    );
            return new ResponseEntity<>("Trip registered successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
