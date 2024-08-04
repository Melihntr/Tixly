package com.tixly.ticket.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tixly.ticket.models.dto.TripDTO;
import com.tixly.ticket.models.dto.TripModel;
import com.tixly.ticket.models.dto.TripRowMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Builder
public class Trip {

    @Id
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

    private JdbcTemplate jdbcTemplate;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Trip(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registerTrip(String peronNo, String departureLocationId, String arrivalLocationId, int estimatedTime, Double price, Long companyId, Long busId, LocalDateTime departureTime, String busType, int seatNo) {
        String seatAvailability = initializeSeatAvailability(seatNo);
    
        String sql = "INSERT INTO trips (peronno, departure_location_id, arrival_location_id, estimatedTime, price, companyId, busId, departureTime, state, bustype, seatno, seat_availability) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Aktif', ?, ?, CAST(? AS JSON))";
    
        jdbcTemplate.update(sql, peronNo, departureLocationId, arrivalLocationId, estimatedTime, price, companyId, busId, departureTime, busType, seatNo, seatAvailability);
    }
    
    private String initializeSeatAvailability(int seatNo) {
        // Initialize all seats as 'available'
        List<String> seats = new ArrayList<>();
        for (int i = 0; i < seatNo; i++) {
            seats.add("available");
        }
        // Convert list to JSON string
        try {
            return objectMapper.writeValueAsString(seats);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting to JSON", e);
        }
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

    public boolean isDepartureLocationExist(String departureLocationId) {
        String sql = "SELECT COUNT(*) FROM trips WHERE departure_location_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{departureLocationId}, Integer.class);
        return count != null && count > 0;
    }

    public boolean isDepartureTimeExist(LocalDateTime departureTime) {
        String sql = "SELECT COUNT(*) FROM trips WHERE departureTime = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{departureTime}, Integer.class);
        return count != null && count > 0;
    }

    public Long getCompanyIdByTripId(Long tripId) {
        String sql = "SELECT companyid FROM trips WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{tripId}, Long.class);
    }

    public List<TripModel> getTripsByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("ID list must not be null or empty.");
        }

        String sql = "SELECT * FROM trips WHERE id IN (" +
                     ids.stream()
                        .map(id -> "?")
                        .collect(Collectors.joining(", ")) +
                     ")";

        return jdbcTemplate.query(sql, ids.toArray(), (rs, rowNum) -> new TripModel(
            rs.getLong("id"),
            rs.getString("peronNo"),
            rs.getString("departureLocationId"),
            rs.getString("arrivalLocationId"),
            rs.getInt("estimatedTime"),
            rs.getDouble("price"),
            rs.getLong("companyId"),
            rs.getLong("busId"),
            rs.getObject("departureTime", LocalDateTime.class),
            rs.getString("state"),
            rs.getString("bustype"),
            rs.getInt("seatno"),
            rs.getString("seat_availability")
        ));
    }
    public List<TripModel> getActiveTrips(String departureLocation, String arrivalLocation) {
        // Base SQL query
        StringBuilder sql = new StringBuilder("SELECT * FROM trips WHERE state = 'Aktif'");

        // List to hold parameters
        List<Object> params = new ArrayList<>();

        // Add filters based on provided parameters
        if (departureLocation != null && !departureLocation.isEmpty()) {
            sql.append(" AND departure_location_id = ?");
            params.add(departureLocation);
        }

        if (arrivalLocation != null && !arrivalLocation.isEmpty()) {
            sql.append(" AND arrival_location_id = ?");
            params.add(arrivalLocation);
        }

        // Execute query
        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> new TripModel(
            rs.getLong("id"),
            rs.getString("peronNo"),
            rs.getString("departure_location_id"),
            rs.getString("arrival_location_id"),
            rs.getInt("estimatedTime"),
            rs.getDouble("price"),
            rs.getLong("companyId"),
            rs.getLong("busId"),
            rs.getObject("departureTime", LocalDateTime.class),
            rs.getString("state"),
            rs.getString("bustype"),
            rs.getInt("seatno"),
            rs.getString("seat_availability")
        ));
    }
    public void bookSeat(Long tripId, int seatNumber) {
        // Get current seat availability
        String seatAvailabilityJson = jdbcTemplate.queryForObject("SELECT seat_availability FROM trips WHERE id = ?", new Object[]{tripId}, String.class);
    
        // Convert JSON string to list
        List<String> seats;
        try {
            seats = objectMapper.readValue(seatAvailabilityJson, List.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON", e);
        }
    
        // Check if the seat is already sold
        if (seats.get(seatNumber - 1).equals("sold")) {
            throw new IllegalStateException("Seat already sold");
        }
    
        // Mark the seat as sold
        seats.set(seatNumber - 1, "sold");
    
        // Convert list back to JSON string
        String updatedSeatAvailability;
        try {
            updatedSeatAvailability = objectMapper.writeValueAsString(seats);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting to JSON", e);
        }
    
        // Update the database
        updateSeatAvailability(tripId, updatedSeatAvailability);
    }

    private void updateSeatAvailability(Long tripId, String updatedSeatAvailability) {
        String sql = "UPDATE trips SET seat_availability = ? WHERE id = ?";
        jdbcTemplate.update(sql, updatedSeatAvailability, tripId);
    }
    
    public List<TripDTO> getTripsByCompanyId(Long companyId) {
      
        String sql = "SELECT * FROM trips WHERE companyid = ?";

        return jdbcTemplate.query(sql, new Object[]{companyId}, new TripRowMapper());
    }
}

    
    



