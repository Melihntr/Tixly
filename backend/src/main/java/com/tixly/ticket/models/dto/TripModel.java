package com.tixly.ticket.models.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.persistence.Column;

import org.springframework.jdbc.core.RowMapper;

import com.tixly.ticket.entity.Trip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripModel implements RowMapper<Trip> {

    private Long id;
    private String peronNo;  
    private String departureLocationId; 
    private String arrivalLocationId; 
    private int estimatedTime;          
    private Double price;
    private Long companyId;
    private Long busId;
    private LocalDateTime departureTime;
    private String state;
    private String busType;
    private int seatNo;
@Column(columnDefinition = "json")
    private String seatAvailability;
    public Trip mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Trip.builder()
                .id(rs.getLong("id"))
                .peronNo(rs.getString("peronno"))
                .departureLocationId(rs.getString("departure_location_id"))
                .arrivalLocationId(rs.getString("arrival_location_id"))
                .estimatedTime(rs.getInt("estimatedtime"))
                .price(rs.getDouble("price"))
                .companyId(rs.getLong("companyid"))
                .busId(rs.getLong("busid"))
                .departureTime(rs.getObject("departuretime", LocalDateTime.class))
                .state(rs.getString("state"))
                .busType(rs.getString("bustype"))
                .seatNo(rs.getInt("seatno"))
                .seatAvailability(rs.getString("seat_availability"))
                .build();
    }
}