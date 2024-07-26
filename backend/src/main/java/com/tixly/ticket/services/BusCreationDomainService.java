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
        Bus busEntity = entityService.getBus();
        Seat seatEntity = entityService.getSeat();
        if(busEntity.isBusExist(plateNo)){
            throw new IllegalStateException("a Bus with given plateNo already exists");
        }
        busEntity.createBus(plateNo,companyId,busType,seatNo);
            seatEntity.registerSeat(seatNo, busEntity.getBusIdByPlateNo(plateNo));
    }
    public void deleteBus(String plateNo){
        Bus busEntity = entityService.getBus();
        Seat seatEntity = entityService.getSeat();
        Trip tripEntity = entityService.getTrip();
        Long busId =busEntity.getBusIdByPlateNo(plateNo); 
        if(tripEntity.isBusInActiveOrFutureTrips(busId)){
            throw new IllegalStateException("The deletion of this bus is not permitted as it is associated with upcoming trips.");
        }
        seatEntity.deleteSeats(busId);
        busEntity.deleteBus(plateNo);  
    }
}
