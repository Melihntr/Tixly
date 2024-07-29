package com.tixly.ticket.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tixly.ticket.utils.ValidUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Bus {  

    private JdbcTemplate jdbcTemplate;
    private final ValidUtil validUtil;

    @Id
    private Long id;
    private String plateNo;
    private Long companyId;
    private String busType;
    private int seatNo;

    public Bus(JdbcTemplate jdbcTemplate,ValidUtil validUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.validUtil = validUtil;
    }

    public void createBus(String plateNo, Long companyId, String busType, int seatNo) {
        String sql = "INSERT INTO bus (plateno, companyid, bustype, seatno) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, plateNo, companyId, busType, seatNo); 
    }
    public Long getBusIdByPlateNo(String plateNo) {
        String sql = "SELECT id FROM bus WHERE plateno = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{plateNo}, Long.class);
    }
    public Long getCompanyIdbyBusId(Long id) {
        String sql = "SELECT companyid FROM bus WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Long.class);
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

    public boolean isBusExist(String plateNo) {
        String sql = "SELECT COUNT(*) FROM bus WHERE plateno = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{plateNo}, Integer.class);
        return count != null && count > 0;
    }
    public Long getCompanyIdbyId(Long Id) {
        String sql = "SELECT companyid FROM bus WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Long.class);
    }
}
