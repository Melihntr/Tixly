package com.tixly.ticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.tixly.ticket.entity.District;
import com.tixly.ticket.entity.Province;

@Service
public class LocationDomainService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LocationDomainService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        Province.setJdbcTemplate(jdbcTemplate);  // Set JdbcTemplate in Province class
    }

    public List<Province> getAllProvinces() {
        return Province.getAllProvinces();
    }

    public List<District> getAllDistricts() {
        return District.getAllDistricts();
    }

}
