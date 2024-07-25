package com.tixly.ticket.entity;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Province {

    @Id
    private Long id;
    private String name;

    private  JdbcTemplate jdbcTemplate;
    
    // Constructor for creating instances
    public Province(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Constructor for initializing with JdbcTemplate
    public Province(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to retrieve all provinces
    public List<Province> getAllProvinces() {
        String sql = "SELECT * FROM iller";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Province(
            rs.getLong("id"),
            rs.getString("il_adi")
        ));
    }
}