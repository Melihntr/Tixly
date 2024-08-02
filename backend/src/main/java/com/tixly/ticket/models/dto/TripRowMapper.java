package com.tixly.ticket.models.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;


public class TripRowMapper implements RowMapper<TripDTO> {

    @Override
    public TripDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        TripDTO trip = new TripDTO();
        trip.setId(rs.getLong("id"));
        trip.setPeronNo(rs.getString("peronno"));  // Ensure this matches the column name in the database
        trip.setDepartureLocationId(rs.getString("departure_location_id"));
        trip.setArrivalLocationId(rs.getString("arrival_location_id"));
        trip.setEstimatedTime(rs.getInt("estimatedtime"));
        trip.setPrice(rs.getDouble("price"));
        trip.setCompanyId(rs.getLong("companyid"));
        trip.setBusId(rs.getLong("busid"));
        
        // Handle possible null value for Timestamp
        Timestamp departureTime = rs.getTimestamp("departuretime");
        if (departureTime != null) {
            trip.setDepartureTime(departureTime.toLocalDateTime());
        } else {
            trip.setDepartureTime(null); // Or set a default value
        }
        
        trip.setState(rs.getString("state"));
        return trip;
    }
}