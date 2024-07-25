package com.tixly.ticket.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.Bus;

@Service
public class BusCreationDomainService {

    @Autowired
    private EntityService entityService;


    public void registerBus(String plateNo, int companyId, String busType,int seatNo){
        //AdminUser userEntity = entityService.getAdmin();
        Bus bus = entityService.getBus();
            bus.createBus(plateNo,companyId,busType,seatNo);
    }
    public void deleteBus(String plateNo){
        Bus bus = entityService.getBus();
        bus.deleteBus(plateNo);
    }
}
