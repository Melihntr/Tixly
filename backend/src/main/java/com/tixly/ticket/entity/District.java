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
public class District {
    
    @Id
    private Long id;
    private String name;
    private Long provinceId;
    
    private static JdbcTemplate jdbcTemplate;


    public District(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    public static void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        District.jdbcTemplate = jdbcTemplate;
    }

    public static List<District> getAllDistricts() {
        String sql = "SELECT * FROM ilceler";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new District(
            rs.getLong("id"),
            rs.getString("ilce_adi"),
            rs.getLong("il_id")
        ));
    }

    public static List<District> getByProvinceId(Long provinceId) {
        String sql = "SELECT * FROM ilceler WHERE il_id = ?";
        return jdbcTemplate.query(sql, new Object[]{provinceId}, (rs, rowNum) -> new District(
            rs.getLong("id"),
            rs.getString("ilce_adi"),
            rs.getLong("il_id")
        ));
    }
}