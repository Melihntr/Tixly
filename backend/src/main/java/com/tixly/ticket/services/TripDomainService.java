package com.tixly.ticket.services;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.AdminUser;
import com.tixly.ticket.entity.Bus;
import com.tixly.ticket.entity.Company;
import com.tixly.ticket.entity.Trip;
import com.tixly.ticket.utils.BearerUtil;


@Service
public class TripDomainService {

    @Autowired
    private EntityService entityService;

    public void registerTrip(String peronNo, int departureLocationId, int arrivalLocationId, int estimatedTime, Double price, Long busId, LocalDateTime departureTime, String authKey) {
        String jwtToken = BearerUtil.extractToken(authKey);
        AdminUser admin = entityService.getAdmin();
        Long userId=admin.getUserIdByAuthKey(jwtToken);
       
        Long companyId=admin.getCompanyIdByUserId(userId);

        Trip trip = entityService.getTrip();
        if(trip.isPeronNoExist(peronNo) && trip.isDepartureLocationExist(departureLocationId)&&trip.isDepartureTimeExist(departureTime)){
            throw new IllegalArgumentException("There is already a trip planned from this peron at this time");
        }

        Bus bus = entityService.getBus();
        if(!companyId.equals(bus.getCompanyIdbyBusId(busId))){
            throw new IllegalArgumentException("Bus ID does not exist for the given company");
        }
        Company companyEntity = entityService.getCompany();
        if (!companyEntity.isCompanyExist(companyId)) {
            throw new IllegalArgumentException("Company does not exist");
        }

        trip.registerTrip( peronNo, departureLocationId, arrivalLocationId, estimatedTime, price, companyId, busId, departureTime);
    }

    public void cancelTrip(Long tripId,String authKey){
        String jwtToken = BearerUtil.extractToken(authKey);
        AdminUser admin = entityService.getAdmin();
        Long userId=admin.getUserIdByAuthKey(jwtToken);
        if (userId == null) {
            throw new IllegalArgumentException("Token doesn't exist");
        }
        Trip trip = entityService.getTrip();
        if(!admin.getCompanyIdByUserId(userId).equals(trip.getCompanyIdByTripId(tripId))){
            throw new IllegalArgumentException("Admin companyId doesnt match");
        }

        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(currentTime,trip.getDepartureTime(tripId));
        if (duration.toHours() <= 12) {
            throw new IllegalArgumentException("Cannot cancel trip within 12 hours of departure");
        }
        trip.cancelTrip(tripId);
    }
}
