package com.tixly.ticket.entity;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Trip {
    
    @Id
    private Long id;
    private String peronNo;
    private int departureLocationId;
    private int arrivalLocationId;
    private int estimatedTime;
    private Double price;
    private int companyId; 
    private Long busId;

    private JdbcTemplate jdbcTemplate;

    public Trip(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    } 
    public boolean isBusExist(Long busId) {
        String sql = "SELECT COUNT(*) FROM bus WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{busId}, Integer.class);
        return count != null && count > 0;
    }
    public boolean isCompanyExist(Integer companyId) {
        String sql = "SELECT COUNT(*) FROM companies WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{companyId}, Integer.class);
        return count != null && count > 0;
    }

    public void registerTrip(Long id,String peronNo,int departureLocationId,int arrivalLocationId,int estimatedTime, Double price,int companyId, Long busId) {
        
        String sql = "INSERT INTO trips (peronno, departurelocationid, arrivallocationid, estimatedtime, price, companyid, busid) " +
             "VALUES (?, ?, ?, ?, ?, ?, ?)";
jdbcTemplate.update(sql, peronNo, departureLocationId, arrivalLocationId, estimatedTime, price, companyId, busId);
    }
}
