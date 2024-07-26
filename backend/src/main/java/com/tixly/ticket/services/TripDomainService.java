package com.tixly.ticket.services;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.Trip;

@Service
public class TripDomainService {

    @Autowired
    private EntityService entityService;

    public void registerTrip(String peronNo, int departureLocationId, int arrivalLocationId, int estimatedTime, Double price, int companyId, Long busId, LocalDateTime departureTime) {
        Trip trip = entityService.getTrip();
        if(trip.isPeronNoExist(peronNo) && trip.isDepartureLocationExist(departureLocationId)&&trip.isDepartureTimeExist(departureTime)){
            throw new IllegalArgumentException("There is already a trip planned from this peron at this time");
        }
        if (!trip.isBusExist(busId)) {
            throw new IllegalArgumentException("Bus ID does not exist");
        }
        if (!trip.isCompanyExist(companyId)) {
            throw new IllegalArgumentException("Company does not exist");
        }
        trip.registerTrip( peronNo, departureLocationId, arrivalLocationId, estimatedTime, price, companyId, busId, departureTime);
    }
    public void cancelTrip(Long tripId){
        Trip trip = entityService.getTrip();
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(currentTime,trip.getDepartureTime(tripId));
        if (duration.toHours() <= 12) {
            throw new IllegalArgumentException("Cannot cancel trip within 12 hours of departure");
        }
        trip.cancelTrip(tripId);
    }
}
