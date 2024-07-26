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

import com.tixly.ticket.models.request.BusRequest;
import com.tixly.ticket.services.BusCreationDomainService;

@RestController
@RequestMapping("/bus")
public class BusRegisterController {

    @Autowired
    private BusCreationDomainService busService;

    @PostMapping()
    public ResponseEntity<?> registerBus(@RequestBody BusRequest busRequest) {
        try {
            busService.registerBus(
                    busRequest.getPlateNo(),
                    busRequest.getCompanyId(),
                    busRequest.getBusType(),
                    busRequest.getSeatNo());
            return ResponseEntity.ok("Bus registered successfully."); // Return the created bus details
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

    @DeleteMapping("/{plateNo}")
    public ResponseEntity<String> deleteBus(@PathVariable String plateNo) {
        try {
            busService.deleteBus(plateNo);
            return ResponseEntity.ok("Bus deleted successfully.");
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
