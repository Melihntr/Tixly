package com.tixly.ticket.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tixly.ticket.models.dto.TripModel;

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
    private int departureLocationId;
    private int arrivalLocationId;
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

    public void registerTrip(String peronNo, int departureLocationId, int arrivalLocationId, int estimatedTime, Double price, Long companyId, Long busId, LocalDateTime departureTime) {

        String sql = "INSERT INTO trips (peronno, departureLocationId, arrivalLocationId, estimatedTime, price, companyId, busId, departureTime, state) "
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
            rs.getInt("departureLocationId"),
            rs.getInt("arrivalLocationId"),
            rs.getInt("estimatedTime"),
            rs.getDouble("price"),
            rs.getLong("companyId"),
            rs.getLong("busId"),
            rs.getObject("departureTime", LocalDateTime.class),
            rs.getString("state")
        ));
    }
}
