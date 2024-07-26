package com.tixly.ticket.entity;
import java.time.LocalDateTime;

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
    private LocalDateTime departureTime;
    private String state;
       

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

    public void registerTrip(String peronNo,int departureLocationId,int arrivalLocationId,int estimatedTime, Double price,int companyId, Long busId, LocalDateTime departureTime) {
        
        String sql = "INSERT INTO trips (peronno, departureLocationId, arrivalLocationId, estimatedTime, price, companyId, busId, departureTime, state) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Aktif')";
                 

        jdbcTemplate.update(sql, peronNo, departureLocationId, arrivalLocationId, estimatedTime, price, companyId, busId, departureTime);
}
    
    public void cancelTrip(Long tripId) {
        String updateSql = "UPDATE trips SET state = 'İptal' WHERE id = ?";
        jdbcTemplate.update(updateSql, tripId);
    }

    public boolean isBusInActiveOrFutureTrips(Long busId) {
        String checkTripsSql = "SELECT COUNT(*) FROM trips WHERE busid = ?";
        Integer count = jdbcTemplate.queryForObject(checkTripsSql, new Object[]{busId}, Integer.class);
        return count != null && count > 0;
    }

    public LocalDateTime getDepartureTime(Long tripId) {
        String sql = "SELECT departuretime FROM trips WHERE id = ?";
        // Query the database and get the departureTime
        return jdbcTemplate.queryForObject(sql, new Object[]{tripId}, LocalDateTime.class);
    }
    public boolean isPeronNoExist(String peronNo) {
        String sql = "SELECT COUNT(*) FROM trips WHERE peronno = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{peronNo}, Integer.class);
        return count != null && count > 0;
    }
    public boolean isDepartureLocationExist(int departureLocationId) {
        String sql = "SELECT COUNT(*) FROM trips WHERE departureLocationId = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{departureLocationId}, Integer.class);
        return count != null && count > 0;
    }
    public boolean isDepartureTimeExist(LocalDateTime departureTime) {
        String sql = "SELECT COUNT(*) FROM trips WHERE departureTime = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{departureTime}, Integer.class);
        return count != null && count > 0;
    }
}
