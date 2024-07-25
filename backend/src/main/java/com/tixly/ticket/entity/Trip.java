package com.tixly.ticket.entity;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.sql.Timestamp;
import java.time.LocalDateTime; 
import java.time.Duration;


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

    public void registerTrip(Long id,String peronNo,int departureLocationId,int arrivalLocationId,int estimatedTime, Double price,int companyId, Long busId, LocalDateTime departureTime) {
        
        String sql = "INSERT INTO trips (peronno, departurelocationid, arrivallocationid, estimatedtime, price, companyid, busid, departuretime) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, peronNo, departureLocationId, arrivalLocationId, estimatedTime, price, companyId, busId, Timestamp.valueOf(departureTime));
    }
    
    public void cancelTrip(Long tripId) {
        // Fetch departureTime from the trip
        String fetchSql = "SELECT departuretime FROM trips WHERE id = ?";
        LocalDateTime departureTime = jdbcTemplate.queryForObject(fetchSql, new Object[]{tripId}, LocalDateTime.class);
    
        if (departureTime == null) {
            throw new IllegalArgumentException("Trip not found");
        }
    
        // Get the current system time
        LocalDateTime currentTime = LocalDateTime.now();
    
        // Check if the trip is within 12 hours of departure
        Duration duration = Duration.between(currentTime, departureTime);
        if (duration.toHours() < 12) {
            throw new IllegalArgumentException("Cannot cancel trip within 12 hours of departure");
        }
    
        // If the trip can be canceled, proceed with the deletion
        String deleteSql = "DELETE FROM trips WHERE id = ?";
        jdbcTemplate.update(deleteSql, tripId);
    }
}
