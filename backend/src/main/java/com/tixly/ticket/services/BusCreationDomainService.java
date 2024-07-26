package com.tixly.ticket.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.Bus;
import com.tixly.ticket.entity.Seat;
import com.tixly.ticket.entity.Trip;

@Service
public class BusCreationDomainService {

    @Autowired
    private EntityService entityService;


    public void registerBus(String plateNo, int companyId, String busType,int seatNo){
        //AdminUser userEntity = entityService.getAdmin();
        Bus bus = entityService.getBus();
        Seat seat = entityService.getSeat();
        if(bus.isBusExist(plateNo)){
            throw new IllegalStateException("a Bus with given plateNo already exists");
        }
            bus.createBus(plateNo,companyId,busType,seatNo);
            seat.registerSeat(seatNo, bus.getBusIdByPlateNo(plateNo));
    }
    public void deleteBus(String plateNo){
        Bus bus = entityService.getBus();
        Seat seat = entityService.getSeat();
        Trip trip = entityService.getTrip();
        if(trip.isBusInActiveOrFutureTrips(bus.getBusIdByPlateNo(plateNo))){
            throw new IllegalStateException("The deletion of this bus is not permitted as it is associated with upcoming trips.");
        }
        bus.deleteBus(plateNo);
        seat.deleteSeats(bus.getBusIdByPlateNo(plateNo));
    }
}
