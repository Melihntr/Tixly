package com.tixly.ticket.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Seat {

    @Id
    private Long id;
    private Integer no;
    private Long busId;
    private JdbcTemplate jdbcTemplate;

    public Seat(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registerSeats(int seatCount, Long busId) {
        String insertSeatSql = "INSERT INTO seats (no, busid) VALUES (?, ?)";
        
        List<Object[]> batchArgs = new ArrayList<>();
        for (int i = 1; i <= seatCount; i++) {
            batchArgs.add(new Object[] { i, busId });
        }
        
        jdbcTemplate.batchUpdate(insertSeatSql, batchArgs);
    }
    public void deleteSeats(Long busId) {
        String deleteSeatsSql = "DELETE FROM seats WHERE busid = ?";
        jdbcTemplate.update(deleteSeatsSql, busId);
    }
}
