package com.tixly.ticket.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bus {

    private JdbcTemplate jdbcTemplate;

    @Id
    private Long id;
    private String plateNo;
    private int companyId;
    private String busType;
    private int seatNo;
    
    public Bus(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }   
    
    public void createBus(String plateNo, int companyId, String busType,int seatNo) {
        validateBus(busType,seatNo);
        String sql = "INSERT INTO bus (plateno, companyid, bustype, seatno) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, plateNo, companyId, busType, seatNo);
    }

    private void validateBus(String busType,int seatNo) {
        if (!busType.equals("2s1")&&!busType.equals("2s2")) {
            throw new IllegalArgumentException("Invalid bus type. Must be '2s1' or '2s2'.");
        }

        if (seatNo % 3 != 0 && seatNo % 4 != 0) {
            throw new IllegalArgumentException("Invalid seat number. Must be divisible by 3 or 4.");
        }
    }
    public String deleteBus(String plateNo) {
        String sql = "DELETE FROM bus WHERE plateno = ?";
        int rowsAffected = jdbcTemplate.update(sql, plateNo);
        if (rowsAffected > 0) {
            return "Bus deleted successfully.";
        } else {
            throw new IllegalArgumentException("Bus not found.");
        }
    }
}
