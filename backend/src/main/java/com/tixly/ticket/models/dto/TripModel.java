package com.tixly.ticket.models.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.tixly.ticket.entity.Trip;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class TripModel implements RowMapper<Trip> {
    
        private Long id;
        private String peronNo;
        private int departureLocationId;
        private int arrivalLocationId;
        private int estimatedTime;
        private Double price;
        private Long companyId;
        private Long busId;
        private LocalDateTime departureTime;
        private String state;
    
    @Override
    public Trip mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Trip.builder()
                .id(rs.getLong("id"))
                .peronNo(rs.getString("peronNo"))
                .departureLocationId(rs.getInt("departureLocationId"))
                .arrivalLocationId(rs.getInt("arrivalLocationId"))
                .estimatedTime(rs.getInt("estimatedTime"))
                .price(rs.getDouble("price"))
                .companyId(rs.getLong("companyId"))
                .busId(rs.getLong("busId"))
                .departureTime(rs.getObject("departureTime", LocalDateTime.class))
                .state(rs.getString("state"))
                .build();
    }
}