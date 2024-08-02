package com.tixly.ticket.entity;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

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
    private Long seatId;
    private Long tripId;
    private String gender;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Passenger(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> getPassengerIdsByTicketIds(List<Long> ticketIds) {
        String sql = "SELECT id FROM passengers WHERE ticketid IN (" + 
                     ticketIds.stream().map(id -> "?").collect(Collectors.joining(",")) + ")";
        return jdbcTemplate.query(sql, ticketIds.toArray(), (rs, rowNum) -> rs.getLong("id"));
    }
    public List<Long> getTripIdsByPassengerIds(List<Long> passengerIds) {
        String sql = "SELECT tripid FROM passengers WHERE id IN (" + 
                     passengerIds.stream().map(id -> "?").collect(Collectors.joining(",")) + ")";
        return jdbcTemplate.query(sql, passengerIds.toArray(), (rs, rowNum) -> rs.getLong("tripid"));
    }
}
