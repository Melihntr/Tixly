package com.tixly.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/register")
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
            return ResponseEntity.badRequest().body(e.getMessage()); // Return error message
        }
    }

    @DeleteMapping("/delete/{plateNo}")
    public ResponseEntity<String> deleteBus(@PathVariable String plateNo) {
        try {
            busService.deleteBus(plateNo);
            return ResponseEntity.ok("Bus deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
