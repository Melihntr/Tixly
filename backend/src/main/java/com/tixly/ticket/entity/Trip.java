package com.tixly.ticket.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.jdbc.core.JdbcTemplate;

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

    private JdbcTemplate jdbcTemplate;

    public Trip(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registerTrip(String peronNo, String departureLocationId, String arrivalLocationId, int estimatedTime, Double price, Long companyId, Long busId, LocalDateTime departureTime) {

        String sql = "INSERT INTO trips (peronno, departure_location_id, arrival_location_id, estimatedTime, price, companyId, busId, departureTime, state) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Aktif')";

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
            rs.getString("state")
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
            rs.getString("state")
        ));
    }
    public List<TripDTO> getTripsByCompanyId(Long companyId) {
      
        String sql = "SELECT * FROM trips WHERE companyid = ?";

        return jdbcTemplate.query(sql, new Object[]{companyId}, new TripRowMapper());
    }
  
}
    
    



