package com.tixly.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.ECustomer;
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

    ECustomer getCustomer(){
        ECustomer entity = new ECustomer(jdbcTemplate, jwtUtil, ValidUtil);

        return entity;
    }

    
}
