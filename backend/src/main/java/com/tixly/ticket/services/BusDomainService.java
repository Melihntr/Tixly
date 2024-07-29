package com.tixly.ticket.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.AdminUser;
import com.tixly.ticket.entity.Bus;
import com.tixly.ticket.entity.Seat;
import com.tixly.ticket.entity.Trip;
import com.tixly.ticket.utils.BearerUtil;
import com.tixly.ticket.utils.ValidUtil;


@Service
public class BusDomainService {

    @Autowired
    private EntityService entityService;
    @Autowired
    private ValidUtil validUtil;

    public void registerBus(String plateNo, Long companyId, String busType,int seatCount,String authKey){
        String jwtToken = BearerUtil.extractToken(authKey);
        AdminUser admin = entityService.getAdmin();
        Long userId=admin.getUserIdByAuthKey(jwtToken);
        if(!admin.getCompanyIdByUserId(userId).equals(companyId)){
            throw new IllegalArgumentException("Company ID from authKey does not match the provided company ID.");
        }

        Bus busEntity = entityService.getBus();
        busEntity.validateBus(plateNo,busType,seatCount);
        if(busEntity.isBusExist(plateNo)){
            throw new IllegalStateException("a Bus with given plateNo already exists");
        }

        busEntity.createBus(plateNo,companyId,busType,seatCount);
        Seat seatEntity = entityService.getSeat();
        seatEntity.registerSeats(seatCount, busEntity.getBusIdByPlateNo(plateNo));
    }
    public void deleteBus(String plateNo,String authKey){
        String jwtToken = BearerUtil.extractToken(authKey);
        AdminUser admin = entityService.getAdmin();
        Bus busEntity = entityService.getBus();
        Long userId=admin.getUserIdByAuthKey(jwtToken);
        
        if(!admin.getCompanyIdByUserId(userId).equals(busEntity.getCompanyIdbyPlateNo(plateNo))){
            throw new IllegalArgumentException("Company ID from authKey does not match the provided company ID.");
        }
        if (!validUtil.isValidPlate(plateNo)) {
            throw new IllegalArgumentException("Invalid plate number format.");
        }
       
        Trip tripEntity = entityService.getTrip();
        Long busId =busEntity.getBusIdByPlateNo(plateNo); 
        if(tripEntity.isBusInActiveOrFutureTrips(busId)){
            throw new IllegalStateException("The deletion of this bus is not permitted as it is associated with upcoming trips.");
        }
        Seat seatEntity = entityService.getSeat();
        seatEntity.deleteSeats(busId);
        busEntity.deleteBus(plateNo);  
    }
}