package com.tixly.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.models.request.TripRequest;
import com.tixly.ticket.services.TripDomainService;

@RestController
@RequestMapping("/trip")
public class TripController {

    @Autowired
    private TripDomainService tripDomainService;

    @PostMapping()
    public ResponseEntity<String> registerTrip(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authKey, // Add authKey header
            @RequestBody TripRequest tripRequest) {
        try {
            tripDomainService.registerTrip(
                    tripRequest.getPeronNo(),
                    tripRequest.getDepartureLocationId(),
                    tripRequest.getArrivalLocationId(),
                    tripRequest.getEstimatedTime(),
                    tripRequest.getPrice(),
                    tripRequest.getCompanyId(),
                    tripRequest.getBusId(),
                    tripRequest.getDepartureTime(),
                    authKey
            );

            return new ResponseEntity<>("Trip registered successfully", HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> cancelTrip(
            @PathVariable("id") Long tripId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authKey) {
        try {
            tripDomainService.cancelTrip(tripId, authKey);
            return new ResponseEntity<>("Trip canceled successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
