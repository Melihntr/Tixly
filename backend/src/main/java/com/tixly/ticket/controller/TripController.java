package com.tixly.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.models.dto.TripDTO;
import com.tixly.ticket.models.dto.TripModel;
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

    @GetMapping("/trips")
    public ResponseEntity<?> getTripsByAuthKey(
            @RequestHeader("Authorization") String authKey) {
        try {
            List<TripModel> tripIds = tripDomainService.getTripIdsByAuthKey(authKey);
            return new ResponseEntity<>(tripIds, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/active")
    public ResponseEntity<?> getActiveTrips(
            @RequestParam(required = false) String departureLocation,
            @RequestParam(required = false) String arrivalLocation) {
        try {    
            return new ResponseEntity<>(tripDomainService.getActiveTrips(departureLocation, arrivalLocation), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/company")
    public List<TripDTO> getTripsByCompanyId(@RequestHeader("Authorization") String authKey) {
        // Call the service method to get trips by company ID
        return tripDomainService.getTripsByCompanyId(authKey);
    }
}
