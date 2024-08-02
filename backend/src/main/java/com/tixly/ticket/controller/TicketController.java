package com.tixly.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.models.dto.TicketModel;
import com.tixly.ticket.services.TicketDomainService;
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketDomainService ticketDomainService;

    @GetMapping
    public ResponseEntity<?> getTicketsByAuthKey(
            @RequestHeader("Authorization") String authKey) {
        try {
            ticketDomainService.getTicketsByAuthKey(authKey);
            return new ResponseEntity<>(ticketDomainService.getTicketsByAuthKey(authKey), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/add")
    public ResponseEntity<String> addTicket(
        @RequestHeader("Authorization") String authKey,
        @RequestBody TicketModel ticketModel) {
    try {
        ticketDomainService.addTicket(authKey, ticketModel);
        return new ResponseEntity<>("Ticket added successfully.", HttpStatus.CREATED);
    } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("Failed to add ticket: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
}
