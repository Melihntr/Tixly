package com.tixly.ticket.entity;


import java.util.List;

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
public class Province {

    @Id
    private Long id;
    private String name;

    private static JdbcTemplate jdbcTemplate;

    // Static method for setting JdbcTemplate
    public static void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        Province.jdbcTemplate = jdbcTemplate;
    }

    public static List<Province> getAllProvinces() {
        String sql = "SELECT * FROM iller";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Province(
            rs.getLong("id"),
            rs.getString("il_adi")
        ));
    }
}