package com.tixly.ticket.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.AdminUser;
import com.tixly.ticket.entity.Bus;
import com.tixly.ticket.entity.Company;
import com.tixly.ticket.entity.Passenger;
import com.tixly.ticket.entity.Ticket;
import com.tixly.ticket.entity.Trip;
import com.tixly.ticket.entity.User;
import com.tixly.ticket.models.dto.TripDTO;
import com.tixly.ticket.models.dto.TripModel;
import com.tixly.ticket.utils.BearerUtil;

@Service
public class TripDomainService {

    @Autowired
    private EntityService entityService;

    public void registerTrip(String peronNo, String departureLocationId, String arrivalLocationId, int estimatedTime, Double price, Long busId, LocalDateTime departureTime, String authKey) {
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
        String bustype = bus.getBusTypebyBusId(busId);
        int seatno = bus.getSeatNobyBusId(busId);

        trip.registerTrip( peronNo, departureLocationId, arrivalLocationId, estimatedTime, price, companyId, busId, departureTime,bustype,seatno);
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
    
    public List<TripModel> getTripIdsByAuthKey(String authKey) {
        String jwtToken = BearerUtil.extractToken(authKey);
        User user = entityService.getCustomer();
        Long customerId = user.getCustomerIdByAuthKey(jwtToken);
        Ticket ticket = entityService.getTicket();
        List<Long> ticketIds =ticket.getTicketIdsByCustomerId(customerId);
        Passenger passenger = entityService.getPassenger();
        if (ticketIds.isEmpty()) {
            throw new IllegalArgumentException("No tickets found for the customer.");
        }
        // Step 2: Get all passenger IDs for the ticket IDs
        List<Long> passengerIds = passenger.getPassengerIdsByTicketIds(ticketIds);
        if (passengerIds.isEmpty()) {
            throw new IllegalArgumentException("No passengers found for the tickets.");
        }
        // Step 3: Get all trip IDs for the passenger IDs
        List<Long> tripIds = passenger.getTripIdsByPassengerIds(passengerIds);
        if (tripIds.isEmpty()) {
            throw new IllegalArgumentException("No trips found for the passengers.");
        }
        Trip trip = entityService.getTrip();

        List<TripModel> trips = trip.getTripsByIds(tripIds);

        return trips;
    }
    public List<TripModel>getActiveTrips( String departureLocation,String arrivalLocation){
        Trip trip = entityService.getTrip();
        List<TripModel> trips=trip.getActiveTrips(departureLocation, arrivalLocation);
        return trips;
    }
    public List<TripDTO> getTripsByCompanyId(String authKey){
        String jwtToken = BearerUtil.extractToken(authKey);
        AdminUser admin = entityService.getAdmin();
        Long adminId=admin.getUserIdByAuthKey(jwtToken);
        Long companyId=admin.getCompanyIdByUserId(adminId);
        Trip trip = entityService.getTrip();
        return trip.getTripsByCompanyId(companyId);
    }
}
