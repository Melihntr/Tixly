package com.tixly.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping()
    public ResponseEntity<String> registerTrip(@RequestBody TripRequest tripRequest) {
        try {
            // Validate input (if applicable)
            if (tripRequest == null) {
                return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
            }

            tripDomainService.registerTrip(
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
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Data integrity error: " + e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<String> cancelTrip(@PathVariable Long tripId) {
        try {
            tripDomainService.cancelTrip(tripId);
            return new ResponseEntity<>("Trip canceled successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Data integrity error: " + e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
