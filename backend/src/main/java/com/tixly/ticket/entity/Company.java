package com.tixly.ticket.entity;


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
public class Company {

    @Id
    private Long id;
    private String name;
    private String companyNo;
    private String website;
    private String contactPhone;
    private String contactEmail;
    private String logoUrl;

     private JdbcTemplate jdbcTemplate;

    public Company(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    } 
    

    public boolean isCompanyExist(Long companyId) {
        String sql = "SELECT COUNT(*) FROM companies WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{companyId}, Integer.class);
        return count != null && count > 0;
    }
}
