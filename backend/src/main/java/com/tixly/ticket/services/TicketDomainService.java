package com.tixly.ticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.Ticket;
import com.tixly.ticket.entity.User;
import com.tixly.ticket.models.dto.TicketModel;
import com.tixly.ticket.utils.BearerUtil;


@Service
public class TicketDomainService {
     @Autowired
    private EntityService entityService;
public List<TicketModel> getTicketsByAuthKey(String authKey) {
        String jwtToken = BearerUtil.extractToken(authKey);
        Ticket ticket = entityService.getTicket();
        User user = entityService.getCustomer();
        Long customerId = user.getCustomerIdByAuthKey(jwtToken);
        if (customerId == null) {
            throw new IllegalArgumentException("Invalid auth key. No associated customer found.");
        }

        return ticket.getTicketsByCustomerId(customerId);
    }

    public void addTicket(String authKey,TicketModel ticketModel) {
        String jwtToken = BearerUtil.extractToken(authKey);
        Ticket ticket = entityService.getTicket();
        User user = entityService.getCustomer();
        Long customerId = user.getCustomerIdByAuthKey(jwtToken);
    
        ticket.addTicket(customerId,ticketModel); 
       
    }
}
