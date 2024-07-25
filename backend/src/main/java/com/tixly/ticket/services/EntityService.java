package com.tixly.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.AdminUser;
import com.tixly.ticket.entity.Bus;
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
        User entity = new User(jdbcTemplate, jwtUtil, ValidUtil);

        return entity;
    }
    AdminUser getAdmin(){
        AdminUser entity = new AdminUser(jdbcTemplate, jwtUtil, ValidUtil);

        return entity;
    }
    Bus getBus(){
        Bus entity = new Bus(jdbcTemplate);

        return entity;
    }
    /*Province getProvince(){
        Province entity = new Province(jdbcTemplate);

        return entity;
    }*/
    
}
