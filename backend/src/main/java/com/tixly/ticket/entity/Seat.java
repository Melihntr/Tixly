package com.tixly.ticket.entity;

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

   public void registerSeat(int seatNo,Long busid) {
        String insertSeatSql = "INSERT INTO seatS (no, busid) VALUES (?, ?)";
        for (int i = 1; i <= seatNo; i++) {
            jdbcTemplate.update(insertSeatSql, i, busid);

        }
    }
    public void deleteSeats(Long busId) {
        String deleteSeatsSql = "DELETE FROM seats WHERE busid = ?";
        jdbcTemplate.update(deleteSeatsSql, busId);
    }
}
