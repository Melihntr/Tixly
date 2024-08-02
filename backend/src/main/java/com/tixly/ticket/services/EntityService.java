package com.tixly.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.AdminUser;
import com.tixly.ticket.entity.Bus;
import com.tixly.ticket.entity.Company;
import com.tixly.ticket.entity.Passenger;
import com.tixly.ticket.entity.Province;
import com.tixly.ticket.entity.Seat;
import com.tixly.ticket.entity.Ticket;
import com.tixly.ticket.entity.Trip;
import com.tixly.ticket.entity.User;
import com.tixly.ticket.utils.JwtUtil;
import com.tixly.ticket.utils.ValidUtil;


@Service
public class EntityService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ValidUtil ValidUtil;

    User getCustomer(){
        return new User(jdbcTemplate, jwtUtil, ValidUtil);    
    }
    AdminUser getAdmin(){
        return new AdminUser(jdbcTemplate, jwtUtil, ValidUtil);
    }
    Bus getBus(){
        return new Bus(jdbcTemplate,ValidUtil);
    }
    Trip getTrip(){
        return new Trip(jdbcTemplate);
    }
    Seat getSeat(){
        return new Seat(jdbcTemplate);
    }
    Province getProvince(){
        return new Province(jdbcTemplate);
    }
    Company getCompany(){
        return new Company(jdbcTemplate);
    }
    Ticket getTicket(){
        return new Ticket(jdbcTemplate);
    }
    Passenger getPassenger(){
        return new Passenger(jdbcTemplate);
    }
}
