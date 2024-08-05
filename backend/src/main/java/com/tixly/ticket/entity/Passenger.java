package com.tixly.ticket.entity;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tixly.ticket.models.dto.PassengerDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ticketId;
    private int  seatId;
    private Long tripId;
    private String gender;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Passenger(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> getPassengerIdsByTicketIds(List<Long> ticketIds) {
        String sql = "SELECT id FROM passenger WHERE ticketid IN (" + 
                     ticketIds.stream().map(id -> "?").collect(Collectors.joining(",")) + ")";
        return jdbcTemplate.query(sql, ticketIds.toArray(), (rs, rowNum) -> rs.getLong("id"));
    }
    public List<Long> getTripIdsByPassengerIds(List<Long> passengerIds) {
        String sql = "SELECT tripid FROM passenger WHERE id IN (" + 
                     passengerIds.stream().map(id -> "?").collect(Collectors.joining(",")) + ")";
        return jdbcTemplate.query(sql, passengerIds.toArray(), (rs, rowNum) -> rs.getLong("tripid"));
    }
   public List<PassengerDTO> getPassengersByTripIds(List<Long> tripIds) {
        String sql = "SELECT * FROM passenger WHERE tripid IN (" + 
                     tripIds.stream().map(id -> "?").collect(Collectors.joining(",")) + ")";
        return jdbcTemplate.query(sql, tripIds.toArray(), (rs, rowNum) -> {
            PassengerDTO passengerDTO = new PassengerDTO();
            passengerDTO.setId(rs.getLong("id"));
            passengerDTO.setTicketId(rs.getLong("ticketid"));
            passengerDTO.setSeatId(rs.getInt("seatid"));
            passengerDTO.setTripId(rs.getLong("tripid"));
            passengerDTO.setGender(rs.getString("gender"));
            return passengerDTO;
        });
    }
   
    public void createPassenger(Long ticketId, Long tripId, int seatId, String gender) {
        // Define the SQL query to insert a new passenger record
        String sql = "INSERT INTO passenger (ticketid, tripid, seatid, gender) VALUES (?, ?, ?, ?)";
        
        
            // Execute the SQL update with individual parameters
            jdbcTemplate.update(sql, ticketId, tripId, seatId, gender);
        
    }
    
}
