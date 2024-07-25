package com.tixly.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.Trip;

@Service
public class TripDomainService {

    @Autowired
    private EntityService entityService;

    public void registerTrip(Long id, String peronNo, int departureLocationId, int arrivalLocationId, int estimatedTime, Double price, int companyId, Long busId) {
        Trip trip = entityService.getTrip();
        if (!trip.isBusExist(busId)) {
            throw new IllegalArgumentException("Bus ID does not exist");
        }
        if (!trip.isCompanyExist(companyId)) {
            throw new IllegalArgumentException("Company does not exist");
        }
        trip.registerTrip(id, peronNo, departureLocationId, arrivalLocationId, estimatedTime, price, companyId, busId);
    }
}
