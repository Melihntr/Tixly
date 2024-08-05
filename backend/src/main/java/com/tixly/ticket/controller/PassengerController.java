package com.tixly.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.models.dto.PassengerDTO;
import com.tixly.ticket.services.PassengerDomainService;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerDomainService passenger;

     @GetMapping()
    public List<PassengerDTO> getPassengersByTripIds(@RequestParam List<Long> tripIds) {
        return passenger.getPassengersByTripIds(tripIds);

    }
}
