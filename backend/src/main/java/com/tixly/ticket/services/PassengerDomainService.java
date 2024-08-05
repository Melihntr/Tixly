package com.tixly.ticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.Passenger;
import com.tixly.ticket.models.dto.PassengerDTO;

@Service
public class PassengerDomainService {

    @Autowired
    private EntityService entityService;

    public List<PassengerDTO> getPassengersByTripIds(List<Long> tripIds) {
        Passenger passenger = entityService.getPassenger();
        return passenger.getPassengersByTripIds(tripIds);
    }
}